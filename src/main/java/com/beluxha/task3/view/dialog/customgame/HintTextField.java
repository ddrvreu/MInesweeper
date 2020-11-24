package com.beluxha.task3.view.dialog.customgame;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class HintTextField extends JTextField implements FocusListener {

    private String hint;
    private boolean isHintVisible;
    private boolean wasHintShown;

    public HintTextField(final String hint, boolean isHintVisible) {
        if (isHintVisible) {
            super.setText(hint);
        }
        this.hint = hint;
        this.wasHintShown = isHintVisible;
        this.isHintVisible = isHintVisible;
        super.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if (isHintVisible) {
            super.setText("");
            isHintVisible = false;
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        if (this.getText().isEmpty() && wasHintShown) {
            super.setText(hint);
            isHintVisible = true;
        }
    }

    @Override
    public String getText() {
        return isHintVisible ? "" : super.getText();
    }

    public void switchOnHint(String hint) {
        this.hint = hint;
        switchOnHint();
    }

    public void switchOnHint() {
        super.setText(hint);
        isHintVisible = true;
        wasHintShown = true;
    }
}
