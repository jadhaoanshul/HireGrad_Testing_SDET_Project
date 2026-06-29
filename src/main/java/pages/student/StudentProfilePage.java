package pages.student;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class StudentProfilePage extends BasePage {

    private final WebDriverWait wait;

    public StudentProfilePage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @FindBy(xpath = "//h1[normalize-space()='My profile']")
    private WebElement profilePageHeading;

    @FindBy(id = "profile-edit-toggle-btn")
    private WebElement editProfileButton;

    @FindBy(xpath = "//button[normalize-space()='Cancel']")
    private WebElement cancelButton;

    @FindBy(xpath = "//button[contains(normalize-space(),'Save')]")
    private WebElement saveChangesButton;

    @FindBy(id = "profile-logout-btn")
    private WebElement logoutButton;

    @FindBy(id = "profile-first-name-input")
    private WebElement firstNameInput;

    @FindBy(id = "profile-middle-name-input")
    private WebElement middleNameInput;

    @FindBy(id = "profile-last-name-input")
    private WebElement lastNameInput;

    @FindBy(id = "profile-address-input")
    private WebElement addressInput;

    @FindBy(id = "profile-institute-email-input")
    private WebElement instituteEmailInput;

    @FindBy(id = "profile-personal-email-input")
    private WebElement personalEmailInput;

    @FindBy(id = "profile-country-code-select")
    private WebElement countryCodeSelect;

    @FindBy(id = "profile-phone-input")
    private WebElement phoneInput;

    @FindBy(css = "input[formcontrolname='tenthSchool']")
    private WebElement tenthSchoolInput;

    @FindBy(css = "input[formcontrolname='tenthPercent']")
    private WebElement tenthPercentInput;

    @FindBy(css = "input[formcontrolname='twelfthSchool']")
    private WebElement twelfthSchoolInput;

    @FindBy(css = "input[formcontrolname='twelfthPercent']")
    private WebElement twelfthPercentInput;

    @FindBy(css = "input[formcontrolname='college']")
    private WebElement collegeInput;

    @FindBy(css = "select[formcontrolname='course']")
    private WebElement courseSelect;

    @FindBy(css = "select[formcontrolname='passOutYear']")
    private WebElement passOutYearSelect;

    @FindBy(css = "input[formcontrolname='cgpa']")
    private WebElement cgpaInput;

    @FindBy(css = "input[formcontrolname='resumeLink']")
    private WebElement resumeLinkInput;

    @FindBy(xpath = "//button[normalize-space()='Academic details']")
    private WebElement academicDetailsToggle;

    @FindBy(xpath = "//span[contains(@class,'rounded-full')]")
    private List<WebElement> skillTags;

    @FindBy(xpath = "//p[normalize-space()='No resume uploaded.']")
    private List<WebElement> noResumeMessage;

    public boolean isStudentProfilePageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(profilePageHeading)).isDisplayed();
    }

    public void clickEditProfileButton() {
        wait.until(ExpectedConditions.elementToBeClickable(editProfileButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(firstNameInput));
    }

    public void clickSaveChangesButton() {
        wait.until(ExpectedConditions.elementToBeClickable(saveChangesButton)).click();
    }

    public void clickCancelButton() {
        wait.until(ExpectedConditions.elementToBeClickable(cancelButton)).click();
    }

    public void clickLogoutButton() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutButton)).click();
    }

    public void enterFirstName(String firstName) {
        clearAndType(firstNameInput, firstName);
    }

    public void enterMiddleName(String middleName) {
        clearAndType(middleNameInput, middleName);
    }

    public void enterLastName(String lastName) {
        clearAndType(lastNameInput, lastName);
    }

    public void enterAddress(String address) {
        clearAndType(addressInput, address);
    }

    public void enterInstituteEmail(String email) {
        clearAndType(instituteEmailInput, email);
    }

    public void enterPersonalEmail(String email) {
        clearAndType(personalEmailInput, email);
    }

    public void selectCountryCode(String countryCode) {
        wait.until(ExpectedConditions.elementToBeClickable(countryCodeSelect));
        new Select(countryCodeSelect).selectByValue(countryCode);
    }

    public void enterPhoneNumber(String phoneNumber) {
        clearAndType(phoneInput, phoneNumber);
    }

    public void enterTenthSchool(String schoolName) {
        clearAndType(tenthSchoolInput, schoolName);
    }

    public void enterTenthPercentage(String percentage) {
        clearAndType(tenthPercentInput, percentage);
    }

    public void enterTwelfthSchool(String schoolName) {
        clearAndType(twelfthSchoolInput, schoolName);
    }

    public void enterTwelfthPercentage(String percentage) {
        clearAndType(twelfthPercentInput, percentage);
    }

    public void enterCollegeName(String collegeName) {
        clearAndType(collegeInput, collegeName);
    }

    public void selectCourse(String courseName) {
        wait.until(ExpectedConditions.elementToBeClickable(courseSelect));
        new Select(courseSelect).selectByVisibleText(courseName);
    }

    public void selectPassOutYear(String year) {
        wait.until(ExpectedConditions.elementToBeClickable(passOutYearSelect));
        new Select(passOutYearSelect).selectByVisibleText(year);
    }

    public void enterCgpa(String cgpa) {
        clearAndType(cgpaInput, cgpa);
    }

    public void enterResumeLink(String resumeLink) {
        clearAndType(resumeLinkInput, resumeLink);
    }

    public boolean isNoResumeMessageDisplayed() {
        return !noResumeMessage.isEmpty() && noResumeMessage.get(0).isDisplayed();
    }

    public int getSkillsCount() {
        return skillTags.size();
    }

    public String getSkillName(int index) {
        return skillTags.get(index).getText();
    }

    public void updatePersonalInformation(String firstName, String middleName, String lastName,
                                          String address, String instituteEmail,
                                          String personalEmail, String countryCode,
                                          String phoneNumber) {
        enterFirstName(firstName);
        enterMiddleName(middleName);
        enterLastName(lastName);
        enterAddress(address);
        enterInstituteEmail(instituteEmail);
        enterPersonalEmail(personalEmail);
        selectCountryCode(countryCode);
        enterPhoneNumber(phoneNumber);
    }

    public void updateAcademicDetails(String tenthSchool, String tenthPercentage,
                                      String twelfthSchool, String twelfthPercentage,
                                      String collegeName, String course,
                                      String passOutYear, String cgpa) {
        enterTenthSchool(tenthSchool);
        enterTenthPercentage(tenthPercentage);
        enterTwelfthSchool(twelfthSchool);
        enterTwelfthPercentage(twelfthPercentage);
        enterCollegeName(collegeName);
        selectCourse(course);
        selectPassOutYear(passOutYear);
        enterCgpa(cgpa);
    }

    public void updateCompleteProfile(String firstName, String middleName, String lastName,
                                      String address, String instituteEmail,
                                      String personalEmail, String countryCode,
                                      String phoneNumber, String tenthSchool,
                                      String tenthPercentage, String twelfthSchool,
                                      String twelfthPercentage, String collegeName,
                                      String course, String passOutYear,
                                      String cgpa, String resumeLink) {
        updatePersonalInformation(firstName, middleName, lastName, address,
                instituteEmail, personalEmail, countryCode, phoneNumber);

        updateAcademicDetails(tenthSchool, tenthPercentage, twelfthSchool,
                twelfthPercentage, collegeName, course, passOutYear, cgpa);

        enterResumeLink(resumeLink);
    }

    public String getInputValue(WebElement element) {
        return element.getAttribute("value");
    }

    public String getFirstNameValue() {
        return getInputValue(firstNameInput);
    }

    public String getLastNameValue() {
        return getInputValue(lastNameInput);
    }

    public String getPhoneValue() {
        return getInputValue(phoneInput);
    }

    private void clearAndType(WebElement element, String value) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(element));
        input.clear();
        input.sendKeys(value);
    }
}