/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.javapoe;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author game
 */
public class LoginTest {

    // ---------- Test data ----------
    private static final String FIRST_NAME = "Kyle";
    private static final String LAST_NAME  = "Smith";

    private static final String VALID_USERNAME   = "kyl_1";
    private static final String INVALID_USERNAME = "kyle!!!!!!!";

    private static final String VALID_PASSWORD   = "Ch&&sec@ke99!";
    private static final String INVALID_PASSWORD = "password";

    private static final String VALID_CELL   = "+27838968976";
    private static final String INVALID_CELL = "08966553";

    // ---------- Expected system responses ----------
    private static final String USERNAME_ERROR =
            "Username is not correctly formatted, please ensure that your username contains an "
            + "underscore and is no more than five characters in length.";

    private static final String PASSWORD_ERROR =
            "Password is not correctly formatted, please ensure that the password contains at "
            + "least eight characters, a capital letter, a number, and a special character.";

    private static final String CELL_ERROR =
            "Cell phone number incorrectly formatted or does not contain international code.";

    private static final String REGISTRATION_SUCCESS =
            "Username successfully captured.\n"
            + "Password successfully captured.\n"
            + "Cell phone number successfully added.";

    private static final String LOGIN_SUCCESS_MESSAGE =
            "Welcome " + FIRST_NAME + ", " + LAST_NAME + " it is great to see you again.";

    private static final String LOGIN_FAILED_MESSAGE =
            "Username or password incorrect, please try again.";

    /** A fully valid user, rebuilt before every test. */
    private Login validUser;

    public LoginTest() {
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        validUser = new Login(FIRST_NAME, LAST_NAME, VALID_USERNAME, VALID_PASSWORD, VALID_CELL);
    }

    @AfterEach
    public void tearDown() {
        validUser = null;
    }

    /**
     * Helper: builds a Login whose stored username is the one under test.
     *
     * NOTE: checkUserName() reads the field userName, not its parameter,
     * so the username has to be set on the object rather than passed in.
     */
    private Login userWithUsername(String username) {
        return new Login(FIRST_NAME, LAST_NAME, username, VALID_PASSWORD, VALID_CELL);
    }

    // =========================================================
    //  checkUserName
    // =========================================================

    /**
     * Username is correctly formatted: contains an underscore and is no more
     * than five characters long. The system returns: True.
     */
    @Test
    public void testCheckUserName_correctlyFormatted() {
        System.out.println("checkUserName - correctly formatted");
        assertTrue(validUser.checkUserName(VALID_USERNAME));
    }

    /**
     * Username is incorrectly formatted. The system returns: False.
     */
    @Test
    public void testCheckUserName_incorrectlyFormatted() {
        System.out.println("checkUserName - incorrectly formatted");
        Login user = userWithUsername(INVALID_USERNAME);
        assertFalse(user.checkUserName(INVALID_USERNAME));
    }

    /**
     * Boundary: an underscore with exactly five characters is still valid.
     */
    @Test
    public void testCheckUserName_boundaryFiveCharacters() {
        System.out.println("checkUserName - five character boundary");
        assertTrue(userWithUsername("ab_de").checkUserName("ab_de"));
        assertFalse(userWithUsername("ab_def").checkUserName("ab_def"));
    }

    /**
     * Short enough, but no underscore, so it must fail.
     */
    @Test
    public void testCheckUserName_noUnderscore() {
        System.out.println("checkUserName - no underscore");
        assertFalse(userWithUsername("kyle").checkUserName("kyle"));
    }

    // =========================================================
    //  checkPasswordComplexity
    // =========================================================

    /**
     * The password meets the complexity requirements. Returns: True.
     */
    @Test
    public void testCheckPasswordComplexity_meetsRequirements() {
        System.out.println("checkPasswordComplexity - meets requirements");
        assertTrue(validUser.checkPasswordComplexity(VALID_PASSWORD));
    }

    /**
     * The password does not meet the complexity requirements. Returns: False.
     */
    @Test
    public void testCheckPasswordComplexity_doesNotMeetRequirements() {
        System.out.println("checkPasswordComplexity - does not meet requirements");
        assertFalse(validUser.checkPasswordComplexity(INVALID_PASSWORD));
    }

    /**
     * Each requirement checked on its own so a failure points at the cause.
     */
    @Test
    public void testCheckPasswordComplexity_individualRules() {
        System.out.println("checkPasswordComplexity - individual rules");
        assertFalse(validUser.checkPasswordComplexity("Ab1!"),        "too short");
        assertFalse(validUser.checkPasswordComplexity("password1!"),  "no capital letter");
        assertFalse(validUser.checkPasswordComplexity("Password!!"),  "no number");
        assertFalse(validUser.checkPasswordComplexity("Password99"),  "no special character");
        assertTrue(validUser.checkPasswordComplexity("Passw0rd!"),    "meets every rule");
    }

    // =========================================================
    //  checkCellPhoneNumber
    // =========================================================

    /**
     * The cell phone number is correctly formatted. Returns: True.
     */
    @Test
    public void testCheckCellPhoneNumber_correctlyFormatted() {
        System.out.println("checkCellPhoneNumber - correctly formatted");
        assertTrue(validUser.checkCellPhoneNumber(VALID_CELL));
    }

    /**
     * The cell phone number is incorrectly formatted. Returns: False.
     */
    @Test
    public void testCheckCellPhoneNumber_incorrectlyFormatted() {
        System.out.println("checkCellPhoneNumber - incorrectly formatted");
        assertFalse(validUser.checkCellPhoneNumber(INVALID_CELL));
    }

    /**
     * No international code, too long, or containing letters must all fail.
     */
    @Test
    public void testCheckCellPhoneNumber_otherInvalidFormats() {
        System.out.println("checkCellPhoneNumber - other invalid formats");
        assertFalse(validUser.checkCellPhoneNumber("0838968976"),    "no international code");
        assertFalse(validUser.checkCellPhoneNumber("+278389689761"), "one digit too many");
        assertFalse(validUser.checkCellPhoneNumber("+2783896897"),   "one digit too few");
        assertFalse(validUser.checkCellPhoneNumber("+27abcdefghi"),  "letters are not digits");
    }

    // =========================================================
    //  registerUser
    // =========================================================

    /**
     * All three fields valid, so registration succeeds.
     */
    @Test
    public void testRegisterUser_allDetailsValid() {
        System.out.println("registerUser - all details valid");
        String expResult = REGISTRATION_SUCCESS;
        String result = validUser.registerUser(VALID_USERNAME);
        assertEquals(expResult, result);
    }

    /**
     * Badly formatted username returns the username error message.
     */
    @Test
    public void testRegisterUser_invalidUsername() {
        System.out.println("registerUser - invalid username");
        Login user = new Login(FIRST_NAME, LAST_NAME, INVALID_USERNAME, VALID_PASSWORD, VALID_CELL);
        String expResult = USERNAME_ERROR;
        String result = user.registerUser(INVALID_USERNAME);
        assertEquals(expResult, result);
    }

    /**
     * Badly formatted password returns the password error message.
     */
    @Test
    public void testRegisterUser_invalidPassword() {
        System.out.println("registerUser - invalid password");
        Login user = new Login(FIRST_NAME, LAST_NAME, VALID_USERNAME, INVALID_PASSWORD, VALID_CELL);
        String expResult = PASSWORD_ERROR;
        String result = user.registerUser(VALID_USERNAME);
        assertEquals(expResult, result);
    }

    /**
     * Badly formatted cell number returns the cell number error message.
     */
    @Test
    public void testRegisterUser_invalidCellPhoneNumber() {
        System.out.println("registerUser - invalid cell phone number");
        Login user = new Login(FIRST_NAME, LAST_NAME, VALID_USERNAME, VALID_PASSWORD, INVALID_CELL);
        String expResult = CELL_ERROR;
        String result = user.registerUser(VALID_USERNAME);
        assertEquals(expResult, result);
    }

    // =========================================================
    //  loginUser
    // =========================================================

    /**
     * Login successful. The system returns: True.
     */
    @Test
    public void testLoginUser_successful() {
        System.out.println("loginUser - successful");
        assertTrue(validUser.loginUser(VALID_USERNAME, VALID_PASSWORD));
    }

    /**
     * Login failed. The system returns: False.
     */
    @Test
    public void testLoginUser_failed() {
        System.out.println("loginUser - failed");
        assertFalse(validUser.loginUser("wrong", "WrongPass99!"), "both wrong");
        assertFalse(validUser.loginUser("wrong", VALID_PASSWORD), "username wrong");
        assertFalse(validUser.loginUser(VALID_USERNAME, "WrongPass99!"), "password wrong");
    }

    /**
     * Credentials are case sensitive.
     */
    @Test
    public void testLoginUser_isCaseSensitive() {
        System.out.println("loginUser - case sensitive");
        assertFalse(validUser.loginUser("KYL_1", VALID_PASSWORD));
    }

    // =========================================================
    //  returnLoginStatus
    // =========================================================

    /**
     * Successful login returns the welcome message with the user's names.
     */
    @Test
    public void testReturnLoginStatus_loggedIn() {
        System.out.println("returnLoginStatus - logged in");
        String expResult = LOGIN_SUCCESS_MESSAGE;
        String result = validUser.returnLoginStatus(true);
        assertEquals(expResult, result);
    }

    /**
     * Failed login returns the failure message.
     */
    @Test
    public void testReturnLoginStatus_notLoggedIn() {
        System.out.println("returnLoginStatus - not logged in");
        String expResult = LOGIN_FAILED_MESSAGE;
        String result = validUser.returnLoginStatus(false);
        assertEquals(expResult, result);
    }

    /**
     * End to end: register, log in, then confirm the status message.
     */
    @Test
    public void testFullRegistrationAndLoginFlow() {
        System.out.println("full registration and login flow");
        assertEquals(REGISTRATION_SUCCESS, validUser.registerUser(VALID_USERNAME));
        boolean loggedIn = validUser.loginUser(VALID_USERNAME, VALID_PASSWORD);
        assertTrue(loggedIn);
        assertEquals(LOGIN_SUCCESS_MESSAGE, validUser.returnLoginStatus(loggedIn));
    }
}