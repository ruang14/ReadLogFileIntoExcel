/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readlogfileintoexcel;

/**
 *
 * @author ruang
 */
public class EntitiesAndErrors {
    
    private String entityCoverNumber;
    private String entityEmail;
    private String entityError;

    public EntitiesAndErrors() {
    }

    public String getEntityCoverNumber() {
        return entityCoverNumber;
    }

    public void setEntityCoverNumber(String entityCoverNumber) {
        this.entityCoverNumber = entityCoverNumber;
    }

    public String getEntityEmail() {
        return entityEmail;
    }

    public void setEntityEmail(String entityEmail) {
        this.entityEmail = entityEmail;
    }

    public String getEntityError() {
        return entityError;
    }

    public void setEntityError(String entityError) {
        this.entityError = entityError;
    }
    
}
