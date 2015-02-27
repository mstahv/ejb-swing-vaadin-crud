package org.vaadin.vaadinui;

import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

import org.vaadin.domain.model.Customer;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.form.AbstractForm;
import org.vaadin.viritin.label.Header;
import org.vaadin.viritin.layouts.MFormLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

/**
 * A UI component built to modify Customer entities. The used superclass
 * provides binding to the entity object and e.g. Save/Cancel buttons by
 * default. In larger apps, you'll most likely have your own customized super
 * class for your forms.
 * <p>
 * Note, that the advanced bean binding technology in Vaadin is able to take
 * advantage also from Bean Validation annotations that are used also by e.g.
 * JPA implementation. Check out annotations in Customer objects email field and
 * how they automatically reflect to the configuration of related fields in UI.
 * </p>
 */
public class CustomerForm extends AbstractForm<Customer> {

    // Prepare some basic field components that our bound to entity property
    // by naming convetion, you can also use PropertyId annotation
    TextField firstName = new MTextField("First name").withFullWidth();
    TextField lastName = new MTextField("Last name").withFullWidth();
    TextField email = new MTextField("Email").withFullWidth();

    @Override
    protected Component createContent() {

        setStyleName(ValoTheme.LAYOUT_CARD);

        return new MVerticalLayout(
                new Header("Edit customer").setHeaderLevel(3),
                // Form layout puts caption on left, component on right
                new MFormLayout(
                        firstName,
                        lastName,
                        email
                ).withFullWidth(),
                getToolbar()
        ).withStyleName(ValoTheme.LAYOUT_CARD);
    }

    @Override
    protected void adjustResetButtonState() {
        // Always true, closes the form even if not modified
        getResetButton().setEnabled(true);
    }

}
