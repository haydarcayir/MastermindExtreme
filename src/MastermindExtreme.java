import java.util.Scanner;
import java.util.Vector;

public class MastermindExtreme {

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		System.out.print("Would you like to play against computer? (Y/n):");
		String againstComputer = scn.nextLine();
		boolean b1 = true;

		
	    if (againstComputer.equals("n")) {
			b1 = false;
		}

		Scanner scan = new Scanner(System.in);
		int digitCount;

		while (true) {
			System.out.print("Enter the number of digits:");
			if (scan.hasNextInt()) {
				digitCount = scan.nextInt();
				break;
			} else {
				System.out.println("invalid value");
				scan.next();
			}
		}

		boolean allowRepeat = true;

		if (digitCount <= 10) {
			System.out.print("Would you like to repeating numbers? (Y/n):");
			Scanner sc = new Scanner(System.in);
			String s4 = sc.nextLine();
			
			if (s4.equals("n")) {
				allowRepeat = false;
			} else {
				allowRepeat = true;
			}
		}

		String ss = generateNumber(digitCount, allowRepeat);
		Scanner input = new Scanner(System.in);
		boolean found = false;

		
		Vector<Shot> shots = new Vector<Shot>();

		for (int i = 1; i < 15 ; i++) {
			System.out.print("Enter " + i + ".  guess:");

			String x = input.nextLine();

			if (x.length() != digitCount) {
				System.out.println("You have to enter " + digitCount
						+ " digit !");
				i--;
				continue;
			}
			for (int j = 0; j < digitCount; j++) {
				if (x.charAt(j) >= '0' != x.charAt(j) <= '9') {
					System.out.println("Wrong input !");

				}

			}

			Shot shot = compare(x, ss);
			System.out.println("result:" + shot.getResultString());
			if (shot.plus == digitCount) {
				System.out.println("You win !");

				found = true;

				break;

			} else if (b1) {

				shots.add(shot);

				System.out.println("Computer thinking..");
				try {
					Thread.sleep(500);
				} catch (Exception e) {

				}
				String guess = guessWithAI(digitCount, shots);
				System.out.println("Computer's guess:" + guess);
				Shot guessResult = compare(guess, ss);
				if (guessResult.plus == digitCount) {
					System.out.println("Computer win !");
					found = true;
					break;
				} else {
					shots.add(guessResult);
					System.out.println("Computer's result:" + guessResult.getResultString());
				}

			}

		}

		if (!found) {
			System.out.println("You lost. Number is " + ss);
		}
 }


	public static String guessWithAI(int digitCount, Vector<Shot> previousShots) {

		String emptyStr = "";
		for (int i = 0; i < digitCount; i++) {
			int randomDigit = (int) (Math.random() * 10);
			if (randomDigit == 0 && i == 0) {
				i--;
				continue;

			}

			emptyStr += randomDigit;
		}

		return emptyStr;
	}

	public static Shot compare(String num1, String num2) {
		Shot s = new Shot();
		s.shot = num1;
		
		char num1chars[] = num1.toCharArray();
		char num2chars[] = num2.toCharArray();
		
		for (int i = 0; i < num1chars.length; i++) {
			if (num1chars[i] == num2chars[i]) {
				s.plus++;
				num1chars[i] = '*';
				num2chars[i] = '*';
			}
		}

		for (int i = 0; i < num1chars.length; i++) {
			for (int j = 0; j < num1chars.length; j++) {
				if (num1chars[i] != '*' && num1chars[i] == num2chars[j]) {
					s.minus++;
					num1chars[i] = '*';
					num2chars[j] = '*';
				}
			}

		}

		return s;
	}

	public static String generateNumber(int digit, boolean allowrepeat) {
		String str = "";

		for (int i = 1; i <= digit; i++) {
			int x = (int) (Math.random() * 10);
			if (x == 0 && i == 1) {
				continue;
			}
			if (!allowrepeat) {
				if (str.indexOf("" + x) != -1) {
					i--;
					continue;

				}

			}
			str += x;
		}

		return str;
	}

	public static class Shot {
		String shot;
		int plus;
		int minus;
		
		public String getResultString(){
			String countPlusMinus="";
			for(int i = 0;i<plus;i++){
				countPlusMinus += "+";
			}
			for(int i = 0;i<minus;i++){
				countPlusMinus += "-";
			}
			return countPlusMinus;
		}
	}

}
