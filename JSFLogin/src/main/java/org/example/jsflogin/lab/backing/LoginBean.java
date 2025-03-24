package org.example.jsflogin.lab.backing;

import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ResourceBundle;

@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;
    private boolean termsAccepted;

    public LoginBean() {
    }

    public String login() {
        FacesContext fContext = FacesContext.getCurrentInstance();
        ResourceBundle appMessages = ResourceBundle.getBundle("ApplicationMessages", fContext.getViewRoot().getLocale());

        if ("scott".equals(username) && "tiger".equals(password)) {
            FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR, appMessages.getString("text.oracle"), null);
            fContext.addMessage(null, error);
            return null;
        } else if (username != null && username.equals(password)) {
            return "success.xhtml?faces-redirect=true";
        } else {
            return "failure.xhtml?faces-redirect=true";
        }
    }

    // Gettery i settery
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isTermsAccepted() {
        return termsAccepted;
    }

    public void setTermsAccepted(boolean termsAccepted) {
        this.termsAccepted = termsAccepted;
    }
}