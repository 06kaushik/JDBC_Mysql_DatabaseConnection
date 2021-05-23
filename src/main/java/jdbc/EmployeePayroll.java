package jdbc;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class EmployeePayroll {
		
	    static final Scanner s = new Scanner(System.in);
	
		public static void main(String[] args) throws ClassNotFoundException, SQLException {
			
			System.out.println(" Press 1 to Add Data\n Press 2 to Reterive data\n Press 3 to Update data\n Press 4 to Reterive Data for Particular Date\n "
					          + "Press 5 to Apply Database Function\n");
			int choice = s.nextInt();
			
			switch(choice) {
			case 1:
				  AddData();
				  break;
			case 2:
				  ReteriveData();
				  break;
			case 3:
				  UpdateData();
				  break;
			case 4:
				  ReteriveDataForParticularDate();
				  break;
			case 5:
				  ApplyDatabaseFunction();
				  break;
				 
			}		
		}

		private static void ApplyDatabaseFunction() throws SQLException {
		
			EmployeeRepo repo = new EmployeeRepo();
			repo.applydatabaseFunctions();
			
		}

		private static void ReteriveDataForParticularDate() throws SQLException {
			EmployeeRepo repo = new EmployeeRepo();
			List<EmployeeDetails> infos = repo.findAllForParticularDate();
			infos.forEach(System.out::println);
		}

		private static void UpdateData() throws SQLException {
		
			System.out.println("Enter Id");
		    int id = s.nextInt();
			
			System.out.println("Enter BasicPay");
		    int basicPay = s.nextInt();
			
			EmployeeRepo repo = new EmployeeRepo();
			repo.updatedata(id, basicPay);
				
		}

		private static void ReteriveData() throws SQLException {
			EmployeeRepo repo = new EmployeeRepo();
			System.out.println("Enter Name");
			String Name = s.next();
			List<EmployeeDetails> infos = repo.findAll(Name);
			infos.forEach(System.out::println);
		}

		private static void AddData() throws ClassNotFoundException, SQLException {
			EmployeeDetails details = new EmployeeDetails();
			
			System.out.println("Enter Name");
			details.setName(s.next());
			
			System.out.println("Enter Department");
			details.setDepartment(s.next());
			
			System.out.println("Enter Gender");
			details.setGender(s.next());
			
			System.out.println("Enter BasicPay");
			details.setBasicPay(s.nextInt());
			
			EmployeeRepo repo = new EmployeeRepo();
			repo.insertRecord(details);
			
		}

}