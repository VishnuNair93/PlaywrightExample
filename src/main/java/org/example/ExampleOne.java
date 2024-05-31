package org.example;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class ExampleOne {

    public static void main(String args[]) {

        /*
        Playwright playwright = Playwright.create(); //create playwright server

        //to start execution in traditional chrome, specify channel in launchOptions
        BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        launchOptions.setHeadless(false);

        Browser browser = playwright.firefox().launch(launchOptions); //launch browser, by default headless - specify launchoptions to start window mode
        Page pageOne = browser.newPage();

        pageOne.navigate("https://www.linkedin.com/");
        String title = pageOne.title();
        System.out.println(title);

        browser.close();
        playwright.close();
        */

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("https://www.salesforce.com/in/?ir=1");
            Page page1 = page.waitForPopup(() -> {
                page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Try for free")).click();
            });
            page1.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("start my free trial")).click();
            assertThat(page1.locator("//label[normalize-space()='First name']/following-sibling::span")).containsText("Enter your first name");
            assertThat(page1.locator("//label[normalize-space()='Last name']/following-sibling::span")).containsText("Enter your last name");
            assertThat(page1.locator("//label[normalize-space()='Email']/following-sibling::span")).containsText("Enter a valid email address");
            assertThat(page1.locator("//label[normalize-space()='Job Title']/following-sibling::span")).containsText("Select your title");
            assertThat(page1.locator("//label[normalize-space()='Company']/following-sibling::span")).containsText("Enter your company name");
            assertThat(page1.locator("//label[normalize-space()='Employees']/following-sibling::span")).containsText("Select the number of employees");
            assertThat(page1.locator("//label[normalize-space()='Phone']/following-sibling::span")).containsText("Enter a valid phone number");
            assertThat(page1.locator("//input[@id='SubscriptionAgreement']/../span")).containsText("Please read and agree to the Master Subscription Agreement");
            page1.getByLabel("First name").click();
            page1.getByLabel("First name").fill("123");
            page1.getByLabel("Last name").click();
            page1.getByLabel("Last name").fill("43");
            page1.getByLabel("Email").click();
            page1.getByLabel("Email").fill("243");
            page1.getByLabel("Job Title").selectOption("Sales_Manager_ANZ");
            page1.getByLabel("Company", new Page.GetByLabelOptions().setExact(true)).click();
            page1.getByLabel("Company", new Page.GetByLabelOptions().setExact(true)).fill("32432");
            page1.getByLabel("Employees").selectOption("75");
            page1.getByLabel("Phone").click();
            page1.getByLabel("Phone").fill("abv");
            // page1.pause(); --> to enable debugging, or add PWDEBUG = 1 in Run Configurations
            page1.locator("//input[@id='SubscriptionAgreement']/following-sibling::div[@class='checkbox-ui']").click();
            page1.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("start my free trial")).click();
            assertThat(page1.locator("//label[normalize-space()='Email']/following-sibling::span")).containsText("Enter a valid email address");
            assertThat(page1.locator("//label[normalize-space()='Phone']/following-sibling::span")).containsText("Enter a valid phone number");
        }
    }
}
