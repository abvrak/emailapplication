package emailapp;

public class Email {
    private String firstName;
    private String lastName;
    private String password;
    private String department;
    private int mailboxCapacity;
    private int defaultPasswordLength = 10;
    private String alternateEmail;
    private String email;
    private String companySuffix = "company.com";

    public Email(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

        this.department = setDepartment();
        this.password = randomPassword(defaultPasswordLength);

        this.email = firstName.toLowerCase() + "." + lastName.toLowerCase() +
                "@" + (department.isEmpty() ? "" : department + ".") + companySuffix;
    }

    protected String setDepartment() {
        return "";
    }

    private String randomPassword(int length) {
        String passwordSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%";
        char[] password = new char[length];
        for (int i = 0; i < length; i++) {
            int rand = (int) (Math.random() * passwordSet.length());
            password[i] = passwordSet.charAt(rand);
        }
        return new String(password);
    }

    public void setMailboxCapacity(int capacity) {
        this.mailboxCapacity = capacity;
    }

    public void setAlternateEmail(String altEmail) {
        this.alternateEmail = altEmail;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public int getMailboxCapacity() { return mailboxCapacity; }
    public String getAlternateEmail() { return alternateEmail; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getDepartment() { return department; }
}