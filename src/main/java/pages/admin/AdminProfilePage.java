package pages.admin;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminProfilePage extends BasePage {

    public AdminProfilePage(WebDriver driver){
        super(driver);
    }

    @FindBy(id="adm-profile-first-name-input")
    WebElement firstname;

    @FindBy(id="adm-profile-last-name-input")
    WebElement lastname;

    @FindBy(id="adm-profile-designation-input")
    WebElement designation;


    @FindBy(id="adm-profile-email-input")
    WebElement institute_email;

    @FindBy(id="adm-profile-phone-input")
    WebElement phone;

    @FindBy(id="adm-profile-office-input")
    WebElement office_location;

    @FindBy(id="adm-profile-college-input")
    WebElement college;

    @FindBy(id="adm-profile-department-input")
    WebElement department;

    @FindBy(id="adm-profile-staff-id-input")
    WebElement staffId;

    @FindBy(id="adm-profile-edit-btn")
    WebElement editbtn;

    @FindBy(id="adm-profile-cancel-btn")
    WebElement cancelbtn;

    @FindBy(id="adm-profile-save-btn")
    WebElement saveChangesbtn;


    public void enterFirstName(String firstName) {
        firstname.clear();
        firstname.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        lastname.clear();
        lastname.sendKeys(lastName);
    }

    public void enterDesignation(String desig) {
        designation.clear();
        designation.sendKeys(desig);
    }

    public void enterInstituteMail(String mail) {
        institute_email.clear();
        institute_email.sendKeys(mail);
    }


    public void enterPhoneNumber(String phn) {
        phone.clear();
        phone.sendKeys(phn);
    }

    public void enterOfficeLocation(String location) {
        office_location.clear();
        office_location.sendKeys(location);
    }



    public void enterCollegeName(String col) {
        college.clear();
        college.sendKeys(col);
    }

    public void enterDepartmentName(String depart) {
        department.clear();
        department.sendKeys(depart);
    }

    public void enterStaffId(String id) {
        staffId.clear();
        staffId.sendKeys(id);
    }

    // ==========================================
    // Composite Actions (Optional but Recommended)
    // ==========================================

    /**
     * Fills out the entire profile form at once
     */
    public void updateCompleteProfile(String fName, String lName, String title,
                                      String email, String phone, String location,
                                      String college, String dept, String empId) {
        enterFirstName(fName);
        enterLastName(lName);
        enterDesignation(title);
        enterInstituteMail(email);
        enterPhoneNumber(phone);
        enterOfficeLocation(location);
        enterCollegeName(college);
        enterDepartmentName(dept);
        enterStaffId(empId);
    }

    public void clickEditProfileBtn(){
        editbtn.click();
    }

    public void clickCancelBtn(){
        cancelbtn.click();
    }

    public void clickSaveChangesBtn(){
        saveChangesbtn.click();
    }





}
