package jdbc;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmployeeRepo {
	
	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/payroll_service";
		String username = "root";
		String password = "123456";
		
		try {
			Connection connection = DriverManager.getConnection(url, username, password);
			
			System.out.println("Connected to the Database");
			} catch (SQLException e) {
				System.out.println("oops error!");
				e.printStackTrace();
			}
	}
	
		public void insertRecord(EmployeeDetails details) throws ClassNotFoundException, SQLException {
			Connection connection = null;
			Statement statement = null;
			try {
			//Step1: Load & Register Driver Class
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver ());
			
			//Step2: Establish a MySql Connection
			 connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_service", "root", "123456");
			
			//Step3: Create Statement
			 statement = connection.createStatement();
			
			//Step4: Execute Query
			 String query = "insert into Employee_Payroll(Name,department,gender, basicpay) value('"+details.getName()+"','"+details.getDepartment()+"','"+details.getGender()+"','"+details.getBasicPay()+"')";
			 int result = statement.executeUpdate(query);
			 System.out.print(result);
			
			 }catch (SQLException e) {
				e.printStackTrace();
			 }catch (Exception e) {
				e.printStackTrace();
			 }finally {
			 	if(connection != null) {
				statement.close();
				}
				if(statement != null) {
				connection.close();
				}
			 }	
	
	
	}
		
		public List<EmployeeDetails> findAll(String Name) throws SQLException {
			 List<EmployeeDetails> infos=new ArrayList<EmployeeDetails>();
			
			 Connection connection = null;
			 PreparedStatement prepstatement = null;
			 try {
			//Step1: Load & Register Driver Class
			 DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver ());
			
			//Step2: Establish a MySql Connection
			 connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_service", "root", "123456");
			
			//Step3: Create Statement
			  String query =" select * from Employee_Payroll where Name=?";
			  prepstatement = connection.prepareStatement(query);
			  prepstatement.setString(1, Name);
			 
			//Step4: Execute Query
			 ResultSet resultset = prepstatement.executeQuery();
		
			 while(resultset.next()) {
				EmployeeDetails details = new EmployeeDetails();
				
				int id=resultset.getInt(1);
				details.setId(id);
				
				String name = resultset.getString(2);
				details.setName(name);
				
				String dept = resultset.getString(5);
				details.setDepartment(dept);
				
				String gender = resultset.getString(6);
				details.setGender(gender);
				
				int pay = resultset.getInt(7);
				details.setBasicPay(pay);
				
				infos.add(details);
			 }
			 }catch (SQLException e) {
				e.printStackTrace();
			 }catch (Exception e) {
				e.printStackTrace();
			 }finally {
				
				if(connection != null) {
					connection.close();
				}
				if(prepstatement != null) {
				   prepstatement.close();
				}
			 }
			 return infos;
		}

		public void updatedata(int id, int basicPay) throws SQLException {
			 Connection connection = null;
			 PreparedStatement prepstate = null;
			 try {
			//Step1: Load & Register Driver Class
			 DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver ());
			
			//Step2: Establish a MySql Connection
			 connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_service", "root", "123456");
			
			//Step3: Create Statement
			 String query ="Update payroll set basicPay=? where Id=?";
			 prepstate = connection.prepareStatement(query);
			 prepstate.setFloat(1, basicPay);
			 prepstate.setInt(2, id);
			
			//Step4: Execute Query
			 prepstate.executeUpdate();
			 System.out.print("Records Updated!");
			
			 }catch (SQLException e) {
				e.printStackTrace();
			 }catch (Exception e) {
				e.printStackTrace();
			 }finally {
				if(connection != null) {
				   connection.close();
				}
				if(prepstate != null) {
				   prepstate.close();
				}
			 }	
		}

		public List<EmployeeDetails> findAllForParticularDate() throws SQLException {
		
			List<EmployeeDetails> infos=new ArrayList<EmployeeDetails>();
			Connection connection = null;
			PreparedStatement prepstatement = null;
			try {
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver ());
				
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_service", "root", "123456");
				
				String query ="Select * from payroll where Start between Cast('2020-03-10' as date) and date(now()); ";
				prepstatement = connection.prepareStatement(query);
				
				ResultSet resultset = prepstatement.executeQuery();
				
				while(resultset.next()) {
					EmployeeDetails details = new EmployeeDetails();
					
					int id=resultset.getInt(1);
					details.setId(id);
					
					String name = resultset.getString(2);
					details.setName(name);
					
					String dept = resultset.getString(5);
					details.setDepartment(dept);
					
					String gender = resultset.getString(6);
					details.setGender(gender);
					
					int pay = resultset.getInt(7);
					details.setBasicPay(pay);
					
					infos.add(details);
				}
				}catch (SQLException e) {
					e.printStackTrace();
				}catch (Exception e) {
					e.printStackTrace();
				}finally {
					if(connection != null) {
						connection.close();
					}
					if(prepstatement != null) {
					   prepstatement.close();
					}
				}
				return infos;
		}

		public void applydatabaseFunctions() throws SQLException {
		
			Connection connection = null;
			PreparedStatement prepstatement = null;
			try {
				DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver ());
				
				connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/payroll_service", "root", "123456");
				
				//To Retrieve sum of BasicPay for Male employees
				String query1="Select Sum(basicPay) From Employee_Payroll where Gender ='M' group by Gender";
				prepstatement = connection.prepareStatement(query1);
				ResultSet result1 = prepstatement.executeQuery();
				result1.next();
				String sum1 = result1.getString(1);
				System.out.println("Sum of BasicPay For Male Employees: "+sum1);
				
				//To Retrieve sum of BasicPay for Female employees
				String query2="Select Sum(basicPay) From Employee_Payroll where Gender ='F' group by Gender";
				prepstatement = connection.prepareStatement(query2);
				ResultSet result2 = prepstatement.executeQuery();
				result2.next();
				String sum2 = result2.getString(1);
				System.out.println("Sum of BasicPay For Female Employees: "+sum2);
				
				System.out.println("-----------------------------------------\n");
				
				//To Retrieve Average of BasicPay for Male employees
				String query3="Select Avg(basicPay) From Employee_Payroll where Gender ='M' group by Gender";
				prepstatement = connection.prepareStatement(query3);
				ResultSet result3 = prepstatement.executeQuery();
				result3.next();
				String sum3 = result3.getString(1);
				System.out.println("Average of BasicPay For Male Employees: "+sum3);
				
				//To Retrieve Average of BasicPay for Female employees
				String query4="Select Avg(basicPay) From Employee_Payroll where Gender ='F' group by Gender";
				prepstatement = connection.prepareStatement(query4);
				ResultSet result4 = prepstatement.executeQuery();
				result4.next();
				String sum4 = result4.getString(1);
				System.out.println("Average of BasicPay For Female Employees: "+sum4);
				
				System.out.println("-----------------------------------------\n");
				
				//To Retrieve Minimum  BasicPay for Male employees
				String query5="Select Min(basicPay) From Employee_Payroll where Gender ='M' group by Gender";
				prepstatement = connection.prepareStatement(query5);
				ResultSet result5 = prepstatement.executeQuery();
				result5.next();
				String sum5 = result5.getString(1);
				System.out.println("Min BasicPay among Male Employees: "+sum5);
				
				
				//To Retrieve Minimum  BasicPay for Female employees
				String query6="Select Min(basicPay) From Employee_Payroll where Gender ='F' group by Gender";
				prepstatement = connection.prepareStatement(query6);
				ResultSet result6 = prepstatement.executeQuery();
				result6.next();
				String sum6 = result6.getString(1);
				System.out.println("Min BasicPay among Female Employees: "+sum6);
				
				System.out.println("-----------------------------------------\n");
				
				//To Retrieve Maximum  BasicPay for Male employees
				String query7="Select Max(basicPay) From Employee_Payroll where Gender ='M' group by Gender";
				prepstatement = connection.prepareStatement(query7);
				ResultSet result7 = prepstatement.executeQuery();
				result7.next();
				String sum7 = result7.getString(1);
				System.out.println("Max BasicPay among Male Employees: "+sum7);
				
				//To Retrieve Maximum  BasicPay for Female employees
				String query8="Select Max(basicPay) From Employee_Payroll where Gender ='F' group by Gender";
				prepstatement = connection.prepareStatement(query8);
				ResultSet result8 = prepstatement.executeQuery();
				result8.next();
				String sum8 = result6.getString(1);
				System.out.println("Max BasicPay among Female Employees: "+sum8);
				
				System.out.println("-----------------------------------------\n");
				
				//To count Number of Male Employees
				String query9="Select Count(Gender) From Employee_Payroll where Gender = 'M'";
				prepstatement = connection.prepareStatement(query9);
				ResultSet result9 = prepstatement.executeQuery();
				result9.next();
				int sum9 = result9.getInt(1);
				System.out.println("Number of Male Employees: "+sum9);
				
				//To count Number of Female Employees
				String query10="Select Count(Gender) From Employee_Payroll where Gender = 'F'";
				prepstatement = connection.prepareStatement(query10);
				ResultSet result10 = prepstatement.executeQuery();
				result10.next();
				int sum10 = result10.getInt(1);
				System.out.println("Number of Female Employees: "+sum10);
				
		        }catch (SQLException e) {
			        e.printStackTrace();
		        }catch (Exception e) {
			        e.printStackTrace();
		        }finally {
			         if(connection != null) {
				        connection.close();
			         } 
			         if(prepstatement != null) {
			            prepstatement.close();
			         }
		         }
		}
	
	

}
