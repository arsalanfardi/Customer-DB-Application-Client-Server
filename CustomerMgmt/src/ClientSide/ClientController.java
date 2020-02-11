package ClientSide;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import Model.MessengerPigeon;
import Model.Customer;

public class ClientController{

    private GUI view;
    private Client client;

    public ClientController(GUI view, Client client) {
        this.client = client;
        this.view = view;
        addListeners();
    }

    public static void main(String[] args) {
        ClientController clientApp = new ClientController(new GUI(),  new Client("localhost", 9898));
    }


    /**
     * Helper method that calls the functions of .addActionListener()
     */
    private void addListeners () {
        this.view.addSearchListener(new SearchListener());
        this.view.addClearSearchListener(new ClearSearchListener());
        this.view.addSearchSelectionListener(new SearchSelectionListener());
        this.view.addSaveButtonListener(new SaveListener());
        this.view.addDeleteButtonListener(new DeleteListener());
        this.view.addClearButtonListener(new ClearListener());
        this.view.addWindowListener(new CloseWindow());
        this.view.addAddButtonListener(new AddListener());
    }


        /**
     * Class that implements WindowListener and removes table on close
     */
    class CloseWindow implements WindowListener {
        @Override
        public void windowClosing(WindowEvent e) {
            //TODO: change everywhere it says model to send to server
            //model.removeTable();
            e.getWindow().dispose();
        }

        @Override
        public void windowClosed(WindowEvent e) {

        }

        @Override
        public void windowIconified(WindowEvent e) {

        }

        @Override
        public void windowDeiconified(WindowEvent e) {

        }

        @Override
        public void windowActivated(WindowEvent e) {

        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }

        @Override
        public void windowOpened(WindowEvent e) {

        }
    }

    /**
     * Class that implements ActionListener and defines action performed for search button.
     * The appropriate search function in model is called depending on search criteria.
     */
    class SearchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                MessengerPigeon pidgey = new MessengerPigeon(null, "1");
                String searchParameter = view.getSearchParameter().getText();
                if (view.getCustomerIDRadio().isSelected()) {
                    Integer.parseInt(searchParameter);
                    pidgey.setSearchFields("ID", searchParameter);
                } else if (view.getCustomerLNameRadio().isSelected()) {
                    pidgey.setSearchFields("lName", searchParameter);
                } else if (view.getCustomerTypeRadio().isSelected()) {
                    if (searchParameter.length() > 1) { 
                        JOptionPane.showMessageDialog(null, "Customer type must be 1 character!"); 
                        return; 
                    }
                    pidgey.setSearchFields("cType", searchParameter);
                } else {
                    pidgey.setSearchFields("all", searchParameter);
                }
                client.objectOut(pidgey);
                MessengerPigeon serverResponse = client.objectIn();
                ArrayList<Customer> custArrayList = serverResponse.getCustomer();
                Customer[] custArray = custArrayList.toArray(new Customer[custArrayList.size()]);
                view.setSearchResultsList(custArray);
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "ID must be an integer!");
            }
            
        }
    }

    /**
     * Class that implements ActionListener and defines action performed for clearSearch button.
     * Clears all search criteria inputs.
     */
    class ClearSearchListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.clearSearchResults();
        }
    }

    /**
     * Class that implements ListSelectionListener and defines value changed for searchList.
     * Populates the Customer info area with the selected object in search list
     */
    class SearchSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            Customer Customer = (Customer) view.getResultsList().getSelectedValue();
            if (Customer != null) {
                view.getCustomerIDField().setText(String.valueOf(Customer.getId()));
                view.getCustomerFNameField().setText(Customer.getFirstName());
                view.getCustomerLNameField().setText(Customer.getLastName());
                view.getCustomerAddressField().setText(Customer.getAddress());
                view.getCustomerPCodeField().setText(Customer.getPostalCode());
                view.getCustomerPhoneField().setText(Customer.getPhoneNumber());
                view.getCustomerTypeCombo().setSelectedItem(String.valueOf(Customer.getCustomerType()));
            }
        }
    }

    /**
     * Class that implements ActionListener and defines action performed for save button.
     * Calls method in model to update selected Customer's information
     */
    class SaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int CustomerID = Integer.parseInt(view.getCustomerIDField().getText());
                String fName = view.getCustomerFNameField().getText();
                String lName = view.getCustomerLNameField().getText();
                String address = view.getCustomerAddressField().getText();
                String pCode = view.getCustomerPCodeField().getText();
                String phone = view.getCustomerPhoneField().getText();
                char cType = view.getCustomerTypeCombo().getSelectedItem().toString().charAt(0);
                if (validateAllInputs(fName, lName, address, phone, pCode)) {
                    Customer CustomerToUpd = new Customer(CustomerID, fName, lName, address, pCode, phone, cType);
                    //TODO: change everywhere it says model to send to server
                    //model.updateCustomer(CustomerToUpd);
                    view.getSearchButton().doClick();
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null,"Can't Save! Are you sure you selected a Customer?");
            } catch (NullPointerException npe) {
                JOptionPane.showMessageDialog(null,"Can't Save! Are you sure you selected a Customer TYPE?");
            }
        }
    }

    /**
     * Class that implements ActionListener and defines action performed for delete button.
     * Calls method in model to delete selected user from database
     */
    class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int CustomerID = Integer.parseInt(view.getCustomerIDField().getText());
                //TODO: change everywhere it says model to send to server
                //model.removeCustomer(CustomerID);
                view.getSearchButton().doClick();
                view.getCustomerIDField().setText(null);
                view.getClearButton().doClick();
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null,"Can't delete! Are you sure you selected a Customer?");
            }
        }
    }

    /**
     * Class that implements ActionListener and defines action performed for clear button.
     * Clears all information in Customer information pane (except for Customer ID)
     */
    class ClearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.getCustomerFNameField().setText(null);
            view.getCustomerLNameField().setText(null);
            view.getCustomerAddressField().setText(null);
            view.getCustomerPCodeField().setText(null);
            view.getCustomerPhoneField().setText(null);
            view.getCustomerTypeCombo().setSelectedItem(null);
        }
    }

    /**
     * Class that implements ActionListener and defines action performed for add button.
     * Call method in model to add new Customer to database
     */
    class AddListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                //TODO: change everywhere it says model to send to server
                //int CustomerID = model.getStaticCustomerID();
                String fName = view.getCustomerFNameField().getText();
                String lName = view.getCustomerLNameField().getText();
                String address = view.getCustomerAddressField().getText();
                String pCode = view.getCustomerPCodeField().getText();
                String phone = view.getCustomerPhoneField().getText();
                char cType = view.getCustomerTypeCombo().getSelectedItem().toString().charAt(0);
                if (validateAllInputs(fName, lName, address, phone, pCode)) {
                    //Customer CustomerToAdd = new Customer(CustomerID, fName, lName, address, pCode, phone, cType);
                    //model.addCustomer(CustomerToAdd);
                    view.getSearchButton().doClick();
                }
            } catch (NullPointerException npe) {
                JOptionPane.showMessageDialog(null,"Did you select a Customer TYPE?");
            }
        }
    }

/// HELPER METHODS TO VALIDATE INPUTS

    /**
     * Helper method validates that inputs for adding/saving Customer information follows acceptable format
     * @param fName Customer first name as String
     * @param lName Customer last name as String
     * @param address Customer address as String
     * @param phone Customer phone number as String
     * @param pCode Customer postal code as String
     * @return true if all inputs follow acceptable format, false otherwise
     */
    private boolean validateAllInputs (String fName, String lName, String address, String phone, String pCode) {
        if (checkFName(fName) && checkLName(lName) && checkAddress(address) && checkPhone(phone) && checkPCode(pCode)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Helper method that validates first name format
     * @param fName Customer first name as String
     * @return true if first name format valid, false otherwise
     */
    private boolean checkFName (String fName) {
        if (fName.length() > 20 || fName == "" || fName == null) {
            JOptionPane.showMessageDialog(null,"Please enter a valid first name\n(<20 chars, not blank)");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Helper method that validates last name format
     * @param lName Customer last name as String
     * @return true if last name format valid, false otherwise
     */
    private boolean checkLName (String lName) {
        if (lName.length() > 20 || lName == "" || lName == null) {
            JOptionPane.showMessageDialog(null,"Please enter a valid last name\n(<20 chars, not blank)");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Helper method that validates address format
     * @param address Customer address as String
     * @return true if address format valid, false otherwise
     */
    private boolean checkAddress (String address) {
        if (address.length() > 50 || address == "" || address == null) {
            JOptionPane.showMessageDialog(null,"Please enter a valid address\n(<50 chars, not blank)");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Helper method that validates phone format
     * @param phone Customer phone number as String
     * @return true if address format valid, false otherwise
     */
    private boolean checkPhone (String phone) {
        String regEx = "^\\d{3}-\\d{3}-\\d{4}$";
        Pattern pat = Pattern.compile(regEx);
        Matcher match = pat.matcher(phone);
        if (match.matches()) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null,"Please enter a valid phone number\n(###-###-####)");
            return false;
        }
    }

    /**
     * Helper method that validates postal code
     * @param pCode Customer postal code as String
     * @return true if postal code format valid, false otherwise
     */
    private boolean checkPCode (String pCode) {
        String regEx = "^[A-Z]\\d[A-Z][ ]\\d[A-Z]\\d$";
        Pattern pat = Pattern.compile(regEx);
        Matcher match = pat.matcher(pCode);
        if (match.matches()) {
            return true;
        } else {
            JOptionPane.showMessageDialog(null,"Please enter a valid postal code\n(A#A #A#)");
            return false;
        }
    }


}