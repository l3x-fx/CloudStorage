package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private Helper helper;
	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
		this.helper = new Helper(this.driver);
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	private void doMockSignUp(String firstName, String lastName, String userName, String password) {
		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		helper.enterTextById("inputFirstName", firstName);
		helper.enterTextById("inputLastName", lastName);
		helper.enterTextById("inputUsername", userName);
		helper.enterTextById("inputPassword", password);
		helper.clickById("buttonSignUp");

		Assertions.assertTrue(driver.findElement(By.id("successMsg")).getText().contains("Registered successfully! Log in to continue."));
	}

	private void doLogIn(String userName, String password) {
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		helper.enterTextById("inputUsername", userName);
		helper.enterTextById("inputPassword", password);
		helper.clickById("submitButton");

		webDriverWait.until(ExpectedConditions.titleContains("Home"));
	}

	private void verifyHomeAccess(){
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());
	}

	@Test
	public void testRedirection() throws InterruptedException {
		doMockSignUp("Redirection","Test","RT","123");
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	@Test
	public void testBadUrl() throws InterruptedException {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Thread.sleep(200);
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


//	SIGNUP AND LOVING FLOW
	@Test
	public void testUnauthorizedHomeAccess()  {
//		home page is not accessible without logging in.
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertNotEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());

	}
	@Test
	public void testHomeAccessAfterLogout() throws InterruptedException {
		doMockSignUp("Home","Logout","HL","one");
		doLogIn("HL", "one");

		verifyHomeAccess();

		helper.clickById("logoutButton");

		driver.get("http://localhost:" + this.port + "/home");
		Thread.sleep(600);
		Assertions.assertNotEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());
	}

// NOTES : ADD - EDIT - DELETE
	private void createNote()  {
		helper.clickById("nav-notes-tab");
		helper.clickById("add-new-note");
		helper.enterTextById("note-title", "NOTE TITLE");
		helper.enterTextById("note-description", "NOTE TEXT");
		helper.clickById("note-submit");
		driver.get("http://localhost:" + this.port + "/home");
		helper.clickById("nav-notes-tab");
	}
	@Test
	public void testAddNote() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 2);
		doMockSignUp("Add","Note","AN","123");
		doLogIn("AN", "123");
		createNote();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[starts-with(@id, 'note')]")));
		WebElement note = driver.findElement(By.xpath("//tr[starts-with(@id, 'note')]"));
		Assertions.assertTrue(note.isDisplayed());
	}

	@Test
	public void testEditNote() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 2);

		doMockSignUp("Edit","Note","EN","123");
		doLogIn("EN", "123");

		createNote();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[starts-with(@id, 'note')]")));
		helper.clickByPath("button","edit-note");
		helper.enterTextById("note-description", " EDITED");
		helper.clickById("note-submit");
		driver.get("http://localhost:" + this.port + "/home");
		helper.clickById("nav-notes-tab");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[starts-with(@id, 'note')]")));
		WebElement note = driver.findElement(By.xpath("//td[starts-with(@id, 'text-note')]"));
		Assertions.assertTrue(note.getText().contains("EDITED"));
	}

	@Test
	public void testDeleteNote() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 2);

		doMockSignUp("Delete","Note","DN","123");
		doLogIn("DN", "123");

		createNote();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[starts-with(@id, 'note')]")));

		helper.clickByPath("a", "delete-note");

		driver.get("http://localhost:" + this.port + "/home");

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//tr[starts-with(@id, 'note')]")));
		assertThrows(NoSuchElementException.class, () -> driver.findElement(By.xpath("//tr[starts-with(@id, 'note')]")));
		}

// CREDENTIALS _ ADD - EDIT - DELETE
	private void createCredential()  {
	helper.clickById("nav-credentials-tab");
	helper.clickById("add-new-credential");
	helper.enterTextById("credential-url", "www.test.me");
	helper.enterTextById("credential-username", "USER");
	helper.enterTextById("credential-password", "PASS");
	helper.clickById("credential-submit");
	driver.get("http://localhost:" + this.port + "/home");
	helper.clickById("nav-credentials-tab");
}
	@Test
	public void testAddCredential() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 2);
		doMockSignUp("Add","Cred","AC","123");
		doLogIn("AC", "123");
		createCredential();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[starts-with(@id, 'cred')]")));
		WebElement cred = driver.findElement(By.xpath("//tr[starts-with(@id, 'cred')]"));
		Assertions.assertTrue(cred.isDisplayed());
	}

	@Test
	public void testEditCredential() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 2);

		doMockSignUp("Edit","Cred","EC","123");
		doLogIn("EC", "123");

		createCredential();
		helper.clickByPath("button", "edit-cred");

		helper.enterTextById("credential-username", " EDITED");
		helper.clickById("credential-submit");
		driver.get("http://localhost:" + this.port + "/home");
		helper.clickById("nav-credentials-tab");

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[starts-with(@id, 'cred')]")));
		WebElement note = driver.findElement(By.xpath("//td[starts-with(@id, 'user-cred')]"));
		Assertions.assertTrue(note.getText().contains("EDITED"));
	}
	@Test
	public void testDeleteCredential() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 2);

		doMockSignUp("Delete","Credential","DC","123");
		doLogIn("DC", "123");

		createCredential();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[starts-with(@id, 'delete-cred')]")));
		WebElement button = driver.findElement(By.xpath("//a[starts-with(@id, 'delete-cred')]"));
		button.click();

		driver.get("http://localhost:" + this.port + "/home");

		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//tr[starts-with(@id, 'cred')]")));
		assertThrows(NoSuchElementException.class, () -> driver.findElement(By.xpath("//tr[starts-with(@id, 'cred')]")));
	}

	//FILE UPLOAD

	@Test
	public void testLargeUpload() throws InterruptedException {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}
}
