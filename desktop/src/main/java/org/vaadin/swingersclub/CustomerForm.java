package org.vaadin.swingersclub;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.vaadin.domain.model.Customer;

/**
 *
 * @author Matti Tahvonen
 */
public class CustomerForm extends JPanel implements ActionListener {

    JTextField firstName = new JTextField();
    JTextField lastName = new JTextField();
    JTextField email = new JTextField("yourname@yourdomain.com");
    JButton create = new JButton("Create");
    JButton update = new JButton("Update");
    JButton delete = new JButton("Delete");

    private final SwingApplication application;
    private Customer editedCustomer;

    public CustomerForm(SwingApplication application) {
        this.application = application;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        addWithCaption("First name:", firstName);
        addWithCaption("Last name:", lastName);
        addWithCaption("Email:",  email);

        final Box actionButtons = Box.createHorizontalBox();

        actionButtons.add(create);
        actionButtons.add(update);
        actionButtons.add(delete);

        add(actionButtons);

        create.addActionListener(this);
        update.addActionListener(this);
        delete.addActionListener(this);

        updateButtonStates();

        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == delete) {
            application.getCustomerFacade().remove(editedCustomer);
            application.deselect();
            clear();
        } else {
            Customer c = editedCustomer;
            if (e.getSource() == create) {
                c = new Customer();
            }
            c.setFirstName(firstName.getText());
            c.setLastName(lastName.getText());
            c.setEmail(email.getText());
            application.getCustomerFacade().save(c);
        }
        application.refreshData();
    }

    void editCustomer(Customer c) {
        this.editedCustomer = c;
        firstName.setText(c.getFirstName());
        lastName.setText(c.getLastName());
        email.setText(c.getEmail());
        updateButtonStates();
    }

    void clear() {
        editedCustomer = null;
        firstName.setText("");
        lastName.setText("");
        email.setText("your@email.com");
        updateButtonStates();
    }

    private void updateButtonStates() {
        update.setEnabled(editedCustomer != null);
        delete.setEnabled(editedCustomer != null);
        create.setEnabled(editedCustomer == null);
    }

    private void addWithCaption(String caption, JTextField f) {
        Box box = Box.createHorizontalBox();
        box.add(new JLabel(caption));
        box.add(Box.createHorizontalGlue());
        box.add(f);
        
        add(box);
    }

}
