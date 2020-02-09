package ClientSide;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * View class primarily used for displaying information to the user.
 */
public class GUI extends JFrame {

    //Title Bar component
    private JLabel windowTitle;

    // Search Criteria Components
    private JPanel searchCriteriaPane;
    private ButtonGroup searchCriteriaGroup;
    private JRadioButton customerIDRadio;
    private JRadioButton customerLNameRadio;
    private JRadioButton customerTypeRadio;
    private JButton search;
    private JButton clearSearch;
    private JTextField searchParameter;

    // Search Result Components
    private JPanel searchResultsPane;
    private JScrollPane searchResultsScroll;
    private JList<Customer> searchResultsList;

    // Customer Information Components
    private JPanel customerInfoPane;
    private JTextField customerIDField;
    private JTextField customerFNameField;
    private JTextField customerLNameField;
    private JTextField customerAddressField;
    private JTextField customerPCodeField;
    private JTextField customerPhoneField;
    private JComboBox customerTypeCombo;
    private JButton save;
    private JButton delete;
    private JButton clear;
    private JButton add;

    /**
     * Constructor for main frame of GUI
     * Calls all setup helper methods
     */
    public GUI () {

        super("Customer Management System (CMS-4000)");

        setUpFrame();
        setUpTitleBar();
        setUpSearchCriteriaPane();
        setUpcustomerInfoPane();
        setUpSearchResultsPane();

        setVisible(true);
    }

    /**
     * Helper method to set up main frame
     */
    private void setUpFrame() {
        setSize(new Dimension(500, 600));
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    /**
     * Helper method sets up Title Bar of main frame
     */
    private void setUpTitleBar () {
        //making the title label
        windowTitle = new JLabel("Customer Management Screen");
        windowTitle.setFont(new Font(null, Font.BOLD,18));
        windowTitle.setHorizontalAlignment(JLabel.CENTER);
        windowTitle.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red),
                windowTitle.getBorder()));

        //putting the title in the grid space
        GridBagConstraints gbag = new GridBagConstraints();
        gbag.fill = GridBagConstraints.HORIZONTAL;
        gbag.gridx = 0;
        gbag.gridy = 0;
        gbag.gridwidth = 2;
        gbag.anchor = GridBagConstraints.PAGE_START;
        this.add(windowTitle, gbag);
    }

    /**
     * Helper method sets up Criteria pane of main frame
     */
    private void setUpSearchCriteriaPane () {

        // creating panel to hold radio and click buttons
        searchCriteriaPane = new JPanel();
        searchCriteriaPane.setLayout(new BoxLayout(searchCriteriaPane, BoxLayout.Y_AXIS));
        searchCriteriaPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red),
                searchCriteriaPane.getBorder()));

        // creating components
        searchCriteriaGroup = new ButtonGroup();
        customerIDRadio = new JRadioButton("Customer ID");
        customerLNameRadio = new JRadioButton("Last Name");
        customerTypeRadio = new JRadioButton("Customer Type");
        searchCriteriaGroup.add(customerIDRadio);
        searchCriteriaGroup.add(customerLNameRadio);
        searchCriteriaGroup.add(customerTypeRadio);


        search = new JButton("Search");
        clearSearch = new JButton("Clear Search");

        searchParameter = new JTextField(10);


        JLabel searchCriteriaPaneTitle = new JLabel("Search customers");
        searchCriteriaPaneTitle.setFont(new Font(null, Font.BOLD,14));
        searchCriteriaPaneTitle.setHorizontalAlignment(JLabel.CENTER);

        // adding all components to the panel
        searchCriteriaPane.add(searchCriteriaPaneTitle);
        searchCriteriaPane.add(customerIDRadio);
        searchCriteriaPane.add(customerLNameRadio);
        searchCriteriaPane.add(customerTypeRadio);
        searchCriteriaPane.add(new JLabel("Enter the search parameter below:"));
        searchCriteriaPane.add(searchParameter);
        searchCriteriaPane.add(search);
        searchCriteriaPane.add(clearSearch);

        // putting the panel in the grid space
        GridBagConstraints gbag = new GridBagConstraints();
        gbag.fill = GridBagConstraints.HORIZONTAL;
        gbag.gridx = 0;
        gbag.gridy = 1;
        gbag.weighty = 0.5;
        gbag.weightx = 0.5;
        gbag.anchor = GridBagConstraints.FIRST_LINE_START;
        this.add(searchCriteriaPane, gbag);
    }

    /**
     * Helper method sets up customer info pane of main frame
     */
    private void setUpcustomerInfoPane () {

        // setting up customer info panel
        customerInfoPane = new JPanel();
        customerInfoPane.setLayout(new BoxLayout(customerInfoPane, BoxLayout.Y_AXIS));
        customerInfoPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red),
                customerInfoPane.getBorder()));

        // creating components
        JPanel customerIDPane = new JPanel(new BorderLayout());
        customerIDPane.add("West", new JLabel("Customer ID: "));
        customerIDField = new JTextField(4);
        customerIDField.setEditable(false);
        customerIDPane.add("Center", customerIDField);

        customerFNameField = new JTextField(20);
        customerLNameField = new JTextField(20);
        customerAddressField = new JTextField(50);
        customerPCodeField = new JTextField(7);
        customerPhoneField = new JTextField(12);
        customerTypeCombo = new JComboBox();
        customerTypeCombo.addItem("C");
        customerTypeCombo.addItem("R");
        customerTypeCombo.setSelectedIndex(-1);
        save = new JButton("Save");
        delete = new JButton("Delete");
        clear = new JButton("Clear");
        add = new JButton("Add");


        // adding components to panel
        JLabel customerInfoPaneTitle = new JLabel("Customer Information");
        customerInfoPaneTitle.setFont(new Font(null, Font.BOLD,14));
        customerInfoPaneTitle.setHorizontalAlignment(JLabel.CENTER);

        customerInfoPane.add(customerInfoPaneTitle);
        customerInfoPane.add(customerIDPane);

        customerInfoPane.add(new JLabel("First Name: "));
        customerInfoPane.add(customerFNameField);
        customerInfoPane.add(new JLabel("Last Name: "));
        customerInfoPane.add(customerLNameField);
        customerInfoPane.add(new JLabel("Address: "));
        customerInfoPane.add(customerAddressField);
        customerInfoPane.add(new JLabel("Postal Code: "));
        customerInfoPane.add(customerPCodeField);
        customerInfoPane.add(new JLabel("Phone Number: "));
        customerInfoPane.add(customerPhoneField);
        customerInfoPane.add(new JLabel("Customer Type: "));
        customerInfoPane.add(customerTypeCombo);
        customerInfoPane.add(save);
        customerInfoPane.add(delete);
        customerInfoPane.add(clear);
        customerInfoPane.add(add);

        // adding panel to grid space
        GridBagConstraints gbag = new GridBagConstraints();
        //gbag.fill = GridBagConstraints.VERTICAL;
        gbag.gridx = 1;
        gbag.gridy = 1;
        gbag.gridheight = 2;
        gbag.weightx = 0.5;
        gbag.anchor = GridBagConstraints.PAGE_START;
        this.add(customerInfoPane, gbag);
    }

    /**
     * Helper method sets up search results pane of main frame
     */
    private void setUpSearchResultsPane () {
        GridBagConstraints gbag = new GridBagConstraints();
        searchResultsPane = new JPanel();
        searchResultsPane.setLayout(new BoxLayout(searchResultsPane, BoxLayout.Y_AXIS));
        searchResultsPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.red),
                searchResultsPane.getBorder()));

        searchResultsScroll = new JScrollPane();

        DefaultListModel listModel = new DefaultListModel<Customer>();
        searchResultsList = new JList<>(listModel);
        searchResultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        searchResultsList.setLayoutOrientation(JList.VERTICAL);
        searchResultsList.setVisibleRowCount(-1);


        searchResultsScroll.setViewportView(searchResultsList);
        searchResultsScroll.setSize(150, 150);

        JLabel searchResultsPaneTitle = new JLabel("Search Results: ");
        searchResultsPaneTitle.setFont(new Font(null, Font.BOLD,14));
        searchResultsPaneTitle.setHorizontalAlignment(JLabel.CENTER);
        searchResultsPane.add(searchResultsPaneTitle);
        searchResultsPane.add(searchResultsScroll);


        gbag.fill = GridBagConstraints.BOTH;
        gbag.gridx = 0;
        gbag.gridy = 2;
        gbag.weightx = 0.5;
        gbag.weighty = 0.5;
        gbag.anchor = GridBagConstraints.FIRST_LINE_START;
        this.add(searchResultsPane, gbag);
    }

// ADDING ACTION LISTENERS

    /**
     * Adds action listener to search button.
     */
    public void addSearchListener (ActionListener listenForSearchButton) {
        search.addActionListener(listenForSearchButton);
    }

    /**
     * Adds action listener to clearSearch button.
     */
    public void addClearSearchListener (ActionListener listenForClearSearchButton) {
        clearSearch.addActionListener(listenForClearSearchButton);
    }

    /**
     * Adds list selection listener to search results JList.
     */
    public void addSearchSelectionListener (ListSelectionListener listenForSearchSelection) {
        searchResultsList.addListSelectionListener(listenForSearchSelection);
    }

    /**
     * Adds action listener to save button.
     */
    public void addSaveButtonListener (ActionListener listenForSaveButton) {
        save.addActionListener(listenForSaveButton);
    }

    /**
     * Adds action listener to delete button.
     */
    public void addDeleteButtonListener (ActionListener listenForDeleteButton) {
        delete.addActionListener(listenForDeleteButton);
    }

    /**
     * Adds action listener to clear button.
     */
    public void addClearButtonListener (ActionListener listenForClearButton) {
        clear.addActionListener(listenForClearButton);
    }

    /**
     * Adds action listener to add button.
     */
    public void addAddButtonListener (ActionListener listenForAddButton) {
        add.addActionListener(listenForAddButton);
    }

// OTHER METHODS

    /**
     * Sets the search results JList.
     * @param searchResult array of Customer objects
     */
    public void setSearchResultsList (Customer[] searchResult) {
        this.searchResultsList.setListData(searchResult);
    }

    /**
     * Clears the search results JList, radio button selection, and search parameter text.
     */
    public void clearSearchResults () {
        DefaultListModel listModel = new DefaultListModel();
        listModel.clear();
        this.searchResultsList.setModel(listModel);
        this.searchCriteriaGroup.clearSelection();
        this.searchParameter.setText("");
    }

    /**
     * Gets the search results JList.
     * @return JList containing an array of Customer objects
     */
    public JList getResultsList () {
        return this.searchResultsList;
    }

    /**
     * Gets the customer id text field.
     * @return JText field for customer ID
     */
    public JTextField getCustomerIDField () {
        return this.customerIDField;
    }

    /**
     * Gets the customer first name text field.
     * @return JText field for customer first name
     */
    public JTextField getCustomerFNameField () {
        return this.customerFNameField;
    }

    /**
     * Gets the customer last name text field.
     * @return JText field for customer last name
     */
    public JTextField getCustomerLNameField () {
        return this.customerLNameField;
    }

    /**
     * Gets the customer address text field.
     * @return JText field for customer address
     */
    public JTextField getCustomerAddressField () {
        return this.customerAddressField;
    }

    /**
     * Gets the customer postal code text field.
     * @return JText field for customer postal code
     */
    public JTextField getCustomerPCodeField () {
        return this.customerPCodeField;
    }

    /**
     * Gets the customer phone number text field.
     * @return JText field for customer phone number
     */
    public JTextField getCustomerPhoneField () {
        return this.customerPhoneField;
    }

    /**
     * Gets the customer type combo box.
     * @return JComboBox for customer type
     */
    public JComboBox getCustomerTypeCombo () {
        return this.customerTypeCombo;
    }

    /**
     * Gets the customer id radio button.
     * @return JRadioButton for customer id
     */
    public JRadioButton getCustomerIDRadio () {
        return this.customerIDRadio;
    }

    /**
     * Gets the customer last name radio button.
     * @return JRadioButton for customer last name
     */
    public JRadioButton getCustomerLNameRadio () {
        return this.customerLNameRadio;
    }

    /**
     * Gets the customer type radio button.
     * @return JRadioButton for customer type
     */
    public JRadioButton getCustomerTypeRadio () {
        return this.customerTypeRadio;
    }

    /**
     * Gets the search parameter text field.
     * @return JText field for search parameter
     */
    public JTextField getSearchParameter () {
        return this.searchParameter;
    }

    /**
     * Gets the search button.
     * @return JButton for search
     */
    public JButton getSearchButton () {
        return this.search;
    }

    /**
     * Gets the clear button.
     * @return JButton for clear
     */
    public JButton getClearButton () { return this.clear; }

}
