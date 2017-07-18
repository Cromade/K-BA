package projet.k_ba.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by candice on 18/07/2017.
 */
public class InputFieldTest {

        public static String isEmailAndPasswordValid(String email, String password) {
            String errorMessage = "";
            if (email == null || email.length() == 0) {
                errorMessage += "Email invalide !\n";
            } else{
                Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
                Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
                if (!matcher.find()) {
                    errorMessage += "Email invalide !\n";
                }
            }

            if (password == null || password.length() < 5) {
                errorMessage += "Mot de passe invalide !\n";
            }

            return errorMessage;
        }

        public static String isInputValid(String lastname, String firstname, String username, String email, String password, String birthday, String address, String city, String postalCode) {
            String errorMessage = "";

            if (lastname == null || lastname.length() == 0) {
                errorMessage += "Nom invalide !\n";
            }

            if (firstname == null || firstname.length() == 0) {
                errorMessage += "Prenom invalide !\n";
            }

            if (username == null || username.length() == 0) {
                errorMessage += "Pseudo invalide !\n";
            }

            errorMessage += isEmailAndPasswordValid(email, password);

            boolean birthdayOk = false;
            try {
                if (birthday != null && birthday.length() == 10) {
                    if (birthday.charAt(2) == '/' && birthday.charAt(5) == '/') {

                        int year = Integer.parseInt(birthday.substring(6));
                        int month = Integer.parseInt(birthday.substring(3,5));
                        int day = Integer.parseInt(birthday.substring(0,2));

                        if (month > 0 && month < 12) {

                            if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                                if (day > 0 && day <= 31) {
                                    birthdayOk = true;
                                }
                            }

                            if (month == 4 || month == 6 || month == 9 || month == 11) {
                                if (day > 0 && day <= 30) {
                                    birthdayOk = true;
                                }
                            }

                            if (month == 2) {
                                if (year%4 == 0) {
                                    if (day > 0 && day <= 29) {
                                        birthdayOk = true;
                                    }
                                } else {
                                    if (day > 0 && day <= 28) {
                                        birthdayOk = true;
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (NumberFormatException e) {
            }
            if (birthdayOk == false) errorMessage += "Date de naissance invalide !\n";

            if (address == null || address.length() == 0) {
                errorMessage += "Adresse invalide !\n";
            }

            if (city == null || city.length() == 0) {
                errorMessage += "Ville invalide !\n";
            }

            if (postalCode == null || postalCode.length() == 0) {
                errorMessage += "Code postal invalide !\n";
            }

            return errorMessage;
        }



}
