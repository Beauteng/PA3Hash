import java.util.ArrayList;
import java.util.Scanner;

//*********************************************************
//Code created on: 5/26/2019
//Code last edited on: 5/31/2019
//Code created by: Spencer Raymond
//Associated with: MSU CSCI-232
//Description: This code is implementing a hash functionality.
//Since there was no definition of what was needed for keys
//or values, I decided to make a simple gradebook. This will
//take in names of students and their grades on the midterm.
//When a new student is added, their name is hashed and
//inserted into the table with quadratic probing. Then we
//also have the capability to print the table, search, and
//delete entries. Given more time I would have liked to
//expand on this program and make it so it read and write to
//a file named gradebook.txt and would update and keep all
//inserted, and deleted, values.
//*********************************************************

class HashMe {
	class Hash{
		private String key;
		private int val;
		Hash next;
		public Hash(String key, int val){
			this.key = key;
			this.val = val;
		}
	}
	class HashArray{
		private int arrSize = 10;
		private int capacity = 0;
		ArrayList<Hash> hashArr = new ArrayList<Hash>(arrSize);
		public HashArray() {
			for(int i = 0 ; i < arrSize ; i++) {
				hashArr.add(null);
			}
		}
		public int search(String unhashedkey) {
			int k = unhashedkey.hashCode();
			boolean found = false;
			for(int i = 0 ; i < arrSize ; i++) {
				if(hashArr.get(i)==null) {
					
				}else {
					if(hashArr.get(i).key.hashCode() == k) {
						System.out.println(unhashedkey + " scored " + hashArr.get(i).val + " on the midterm");
						found = true;
					}
				}
			}
			if(found == true) {
				
			}else {
				System.out.println(unhashedkey + " was not found in gradebook\n");
			}
			return 0;
		}
		private int locate(String key) {
			int hashCode = Math.abs(key.hashCode()); 
			int index = hashCode % arrSize; 
			return index; 
		}
		public int getVal(String key) {
			int index,i = locate(key);
			Hash temp = hashArr.get(i);
			int h = 1;
			while (temp != null)
			{
				
				if(temp.key == key) {
					return temp.val;
				}else {
					i = (i + h * h++) % arrSize;
				}
			}
			return 0;
		}
		
		public void insert(String key, int val) {
			int index = locate(key);
			int h = 1;
			int i = index;
			boolean located = false;
			do {
				Hash temp = hashArr.get(i);
				if(temp == null) {
					capacity++; 
					Hash newHash = new Hash(key, val); 
					newHash.next = temp; 
					hashArr.set(i, newHash);
					located = true;
					break;
				}
				if(temp.key == key) {
					temp.val = val;
					return;
				}
				i = (i+ h * h++) % arrSize;
			}while (!located);
			double f = (double)capacity/(double)arrSize;
			if(f >= 0.8) {
				capacity = 0;
				arrSize *= 2;
				ArrayList<Hash> tempHash = hashArr;
				hashArr = new ArrayList<Hash>();
				for (int j = 0 ; j < arrSize ; j++) {
					hashArr.add(null);
				}
				for (Hash start : tempHash) { 
					while (start != null) { 
						insert(start.key, start.val);
						start = start.next;
					}
				}
			}
		}
		public void delete(String unhashedkey) {
			int k = unhashedkey.hashCode();
			boolean found = false;
			for(int i = 0 ; i < arrSize ; i++) {
				if(hashArr.get(i)==null) {
					
				}else {
					if(hashArr.get(i).key.hashCode() == k) {
						hashArr.remove(i);
						hashArr.add(i,null);
						found = true;
					}
				}
			}
			if(found == true) {
				System.out.println(unhashedkey + " has been removed from the gradebook\n");
			}else {
				System.out.println(unhashedkey + " was not found\n");
			}
		}
		public void print() {
			for(int i = 0; i < arrSize; i++) {
				Hash temp = hashArr.get(i);
				if(temp != null){
					System.out.println(i + " " + temp.key + " " + temp.val);
				}else {
				    System.out.println(i + " null null");
				}
			}
		}
	}
	public void Start() {
		HashArray ha = new HashArray();
		boolean run = false;
		while(true) {
			Scanner read1 = new Scanner(System.in);
			System.out.println("Would you like to create a gradebook?(y/n)\n");
			try{
				char i = read1.next().charAt(0);
				if(i == 'y' || i == 'Y') {
					run = true;
					break;
				}else {
					run = false;
					break;
				}
			}catch (java.util.InputMismatchException e) {System.out.println("Please select from the following list\n");}
		}
    	while(run) {
	    	int choice = 0;
	    	Scanner read = new Scanner(System.in);
		    	System.out.println("1: Print table 2: Add a student and midterm score 3: Search student 4: Delete student\n");
		    	try{
		    		choice = read.nextInt();
		    	}catch (java.util.InputMismatchException e) {System.out.println("Please select from the following list\n"); continue;}
		    	switch(choice) {
		    	case 1:
		    		System.out.println("Printing...");
		    		System.out.println("--------------------********--------------------\n");
		    		ha.print();
		    		System.out.println("\n--------------------********--------------------\n");
		    		break;
		    	case 2:
		    		Scanner newVal = new Scanner(System.in);
			    	System.out.println("Who would you like to add to the gradebook? (LastFirst, no spaces)\n");
			    	String s = newVal.next();
			    	System.out.println("What was their score? (Out of 100 ex. 82)\n");
			    	int i = newVal.nextInt();
			    	ha.insert(s, i);
			    	break;
			    case 3:
		    		Scanner searchVal = new Scanner(System.in);
			    	System.out.println("\nWho's grade do you want to search for?\n");
			    	String f = searchVal.next();
			    	ha.search(f);
		    		break;
			    case 4:
		    		Scanner deleteVal = new Scanner(System.in);
			    	System.out.println("\nWho's grade do you want to delete?\n");
			    	String d = deleteVal.next();
			    	ha.delete(d);
		    		break;
		    	default:
		    		System.out.println("Please select from the following list\n");
		    		break;
		    	}
    	}
	}
}
