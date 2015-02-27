package org.vaadin.vaadinui;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.vaadin.cdiviewmenu.ViewMenuItem;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import javax.ejb.EJB;
import org.vaadin.backend.ejb.CustomerFacadeRemote;
import org.vaadin.domain.model.Customer;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTable;
import org.vaadin.viritin.fields.MValueChangeEvent;
import org.vaadin.viritin.fields.MValueChangeListener;
import org.vaadin.viritin.form.AbstractForm.DeleteHandler;
import org.vaadin.viritin.form.AbstractForm.ResetHandler;
import org.vaadin.viritin.form.AbstractForm.SavedHandler;
import org.vaadin.viritin.label.Header;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

/**
 * A view that lists Customers in a Table and lets user to choose one for
 * editing. There is also RIA features like on the fly filtering.
 */
@CDIView("")
@ViewMenuItem(icon = FontAwesome.USERS, order = ViewMenuItem.BEGINNING)
public class CustomerListView extends MVerticalLayout implements View,
        SavedHandler<Customer>, ResetHandler<Customer>, DeleteHandler<Customer> {

    @EJB
    private CustomerFacadeRemote service;

    CustomerForm customerForm = new CustomerForm();

    // Introduce and configure some UI components used on this view
    MTable<Customer> bookTable = new MTable(Customer.class).withFullWidth().
            withFullHeight().withProperties("firstName", "lastName", "email");

    MHorizontalLayout mainContent = new MHorizontalLayout(bookTable).
            withFullWidth().withMargin(false);

    Header header = new Header("Customers").setHeaderLevel(2);

    Button addButton = new MButton(FontAwesome.PLUS_SQUARE,
            new Button.ClickListener() {

                @Override
                public void buttonClick(Button.ClickEvent event) {
                    addBook();
                }
            });

    @PostConstruct
    public void init() {

        /*
         * Add value change listener to table that opens the selected book into
         * an editor.
         */
        bookTable.addMValueChangeListener(new MValueChangeListener<Customer>() {

            @Override
            public void valueChange(MValueChangeEvent<Customer> event) {
                editBook(event.getValue());
            }
        });

        add(
            new MHorizontalLayout(header, addButton)
            .expand(header)
            .alignAll(Alignment.MIDDLE_LEFT)
        );
        expand(mainContent);
        setMargin(true);
        
        listBooks();
    }

    private void listBooks() {
        bookTable.setBeans(new ArrayList<>(service.findAll()));
    }

    void editBook(Customer book) {
        if (book != null) {
            openEditor(book);
        } else {
            closeEditor();
        }
    }

    void addBook() {
        openEditor(new Customer());
    }

    private void openEditor(Customer customer) {
        customerForm.setSavedHandler(this);
        customerForm.setResetHandler(this);
        customerForm.setDeleteHandler(customer.isPersisted() ? this : null);
        mainContent.addComponent(customerForm);
        customerForm.setEntity(customer);
        customerForm.focusFirst();
    }

    private void closeEditor() {
        mainContent.removeComponent(customerForm);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

    @Override
    public void onSave(Customer entity) {
        service.save(entity);
        listBooks();
        closeEditor();
    }

    @Override
    public void onReset(Customer entity) {
        listBooks();
        closeEditor();
    }

    @Override
    public void onDelete(Customer entity) {
        service.remove(entity);
        closeEditor();
        listBooks();
    }

}
