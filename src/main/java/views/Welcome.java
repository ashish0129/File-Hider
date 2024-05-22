package views;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

import dao.UserDAO;
import model.User;
import service.GenerateOTP;
import service.SendOTPService;
import service.UserService;

public class Welcome {

	public void welcomeScreen() {
		// BufferedReader bufferedReader = new BufferedReader(new
		// InputStreamReader(System.in));
		Scanner sc = new Scanner(System.in);

		do {
			System.out.println("Welcome to the file Hiding process:");
			System.out.println("Press 1: Signup");
			System.out.println("Press 2: Login");
			System.out.println("Press 3: Exit");

			int choose = sc.nextInt();

//		try {
//			choose = Integer.parseInt(bufferedReader.readLine());
//		}
//		catch(IOException e)
//		{
//			e.printStackTrace();
//		}

			switch (choose) {
			case 1:
				signup();
				break;
			case 2:
				login();
				break;
			case 3:
				System.exit(3);
			}
		} while (true);
	}

	private void login() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter email");
		String email = sc.nextLine();
		try {
			if (UserDAO.isExists(email)) {
				String genOTP = GenerateOTP.getOTP();
				SendOTPService.sendOTP(email, genOTP);
				System.out.print("Enter the OTP:");
				String otp = sc.nextLine();
				if (otp.equals(genOTP)) {
					new UserView(email).home();
				} else {
					System.out.println("Wrong OTP");
				}
			} else {
				System.out.println("User not found!!");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	private void signup() {
		Scanner sc = new Scanner(System.in);
        System.out.println("Enter name");
        String name = sc.nextLine();
        System.out.println("Enter email");
        String email = sc.nextLine();
        String genOTP = GenerateOTP.getOTP();
        SendOTPService.sendOTP(email, genOTP);
        System.out.println("Enter the OTP:");
        String otp = sc.nextLine();
        if(otp.equals(genOTP)) {
            User user = new User(name, email);
            int response = UserService.saveUser(user);
            switch (response) {
                case 0 : System.out.println("User registered");
                case 1 : System.out.println("User already exists");
            }
        } else {
            System.out.println("Wrong OTP");
        }
	}
}
