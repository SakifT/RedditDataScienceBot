import java.util.Scanner;
import java.util.Vector;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DataBot {

	static int SIZE =6465;

	private static String[] posts;
	private static String[] users;
	private static int[] numberOfComments;

	public DataBot() {
		users = new String[SIZE];
		posts = new String[SIZE];
		numberOfComments = new int[SIZE];
	}

	public void getUsers() {

		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader("redditAutors"));
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		String str;
		int count = 0;

		try {
			while ((str = in.readLine()) != null) {
				users[count] = str;
				count++;
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public void getPosts() {

		BufferedReader im = null;
		try {
			im = new BufferedReader(new FileReader("redditPosts"));
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		String str1;
		int counttwo = 0;

		try {
			while ((str1 = im.readLine()) != null) {
				posts[counttwo] = str1;

				counttwo++;
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public void getNumberOfComments() {

		BufferedReader iy = null;
		try {

			iy = new BufferedReader(new FileReader("redditUpvotes"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		}
		String str2;
		int votetwo = 0;
		try {
			while ((str2 = iy.readLine()) != null) {

				numberOfComments[votetwo] = Integer.parseInt(str2);

				votetwo++;
			}
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	public String enterWord() {
		Scanner input = new Scanner(System.in);
		System.out.println("Please enter a word");
		return input.nextLine();
	}

	public void getTotalCountOfCatchwords(String catchWord) {

		int wordCount = 0;
		for (int a = 0; a < SIZE; a++) {
			for (int i = -1; (i = posts[a].indexOf(catchWord, i + 1)) != -1; i++) {
				wordCount++;
			}
		}

		System.out.println("You have entered the word ["+catchWord+"]. The entered word was mentioned " + wordCount + " times");
	}

	public void getUserCountOfCatchwords(String catchWord) {

		
		//Vector<Integer>  = new Vector<Integer>();

		Vector<String> uniqueUsers = new Vector<String>();

		int [] userCountOfCatchword = new int[6465];
		
		for (int i=0; i<SIZE; i++)
			userCountOfCatchword[i] = 0;

		for (int a = 0; a < SIZE; a++) {
			if (!uniqueUsers.contains(users[a])) {
					uniqueUsers.add(users[a]);
					//userCountOfCatchword.add(0);
			}
			int index = uniqueUsers.indexOf(users[a]);
			
			int count = 0;
			for (int i = -1; (i = posts[a].indexOf(catchWord, i + 1)) != -1; i++) {
				count++;
			}

			userCountOfCatchword[index] += count;

			
		}
		
		int indexOfLargest = getIndexOfLargest(userCountOfCatchword);
		if (userCountOfCatchword[indexOfLargest]>0)
			System.out.println("The most amount of times the word ["+catchWord+"] was used were "+userCountOfCatchword[indexOfLargest]+" times by "+uniqueUsers.get(indexOfLargest)+"\n");
		else {
			System.out.println("The word ["+catchWord+"] was not mentioned by any user");
		}
				
		//for (int a = 0; a < uniqueUsers.size(); a++)
			//System.out.println(uniqueUsers.elementAt(a) + " " + userCountOfCatchword[a]);

		
		findUserPosts(catchWord, uniqueUsers.get(indexOfLargest));


	}
	
	
		public void findUserPosts(String catchWord, String username) {
			System.out.println("The post(s) that included ["+catchWord+"] were\n");
			for (int i = 0; i < SIZE; i++) {
				if (username.equals(users[i])) {
					
					if (posts[i].indexOf(catchWord) != -1) {
						System.out.println(posts[i]);
					}				
				}
			}
		}
		

	public void countLargest(String catchWord) {
		int indexLargestNumberOfComments = 0;
		int largestNumberOfComments = 0;
		for (int i = 0; i < numberOfComments.length; i++) {

			int numCommentsPost = numberOfComments[i];
			if (numCommentsPost > largestNumberOfComments) {

				largestNumberOfComments = numCommentsPost;
				indexLargestNumberOfComments = i;
			}

		}
		System.out.println("\nThe user " + users[indexLargestNumberOfComments]
				+ " wrote the largest amount of comments (" + largestNumberOfComments + " comments) on the thread\n'"
				+ posts[largestNumberOfComments] + "'");

	}

	public int getIndexOfLargest(int a[]) {
		int indexOfLargest = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] > a[indexOfLargest]) {
				indexOfLargest = i;
			}
		}
		return indexOfLargest;
	}

	public static void main(String[] args) {

		DataBot db = new DataBot();

		db.getUsers();
		db.getPosts();
		db.getNumberOfComments();

		System.out.println("Welcome to the DataBot. This bot is based on reddit posts from the subreddit /r/TheDonald.\n");

		System.out.println("After entering a word, the bot will display: \n");
		
		System.out.println("1. How many times the world was used.\n");
		System.out.println("2.Which user used that word the most.  \n");
		System.out.println("3.The posts that included the word. \n");




		
		String catchWord = db.enterWord();

		db.getTotalCountOfCatchwords(catchWord);
		db.getUserCountOfCatchwords(catchWord);
		//db.countLargest(catchWord);
	}

}
