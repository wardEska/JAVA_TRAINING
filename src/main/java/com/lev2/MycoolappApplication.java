package com.lev2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
		scanBasePackages = {"com.lev2"  } // you should do this ti scan all the packages
)
public class MycoolappApplication {

	public static void main(String[] args) {

		SpringApplication.run(MycoolappApplication.class, args);
	}

	// Executed after the spring beans loaded
//	@Bean
//	CommandLineRunner commandLineRunner(StudentDAO studentDAO) {
//		return  runner->{
//			crud(studentDAO);
//		};
//	}

//	private Scanner scanner = new Scanner(System.in);
//	private void crud(StudentDAO studentDAO){
//		String op;
//		do{
//			System.out.println("To Insert Please press 1 :  ");
//			System.out.println("To Get by Id Please press 2 :  ");
//			System.out.println("To Get All Please press 3 :  ");
//			System.out.println("To Search Please press 4 :  ");
//			System.out.println("To Update Please press 5 :  ");
//			System.out.println("To Delete Please press 6 :  ");
//			System.out.println("To Exit Please press 0 :  ");
//
//
//			System.out.print("please enter the operation :  ");
//			op = scanner.nextLine();
//			switch (op){
//				case "1":
//					createStudent(studentDAO);
//					break;
//				case "2":
//					getById(studentDAO);
//					break;
//				case "3":
//					getAll(studentDAO);
//					break;
//				case "4":
//					searchStudents(studentDAO);
//					break;
//				case "5":
//					updateStudent(studentDAO);
//					break;
//				case "6":
//					delete(studentDAO);
//					break;
//				case "0":
//					System.out.print("Exiting ...");
//					break;
//				default:
//					System.out.print("wrong operation.");
//
//			}
//		}while (!op.equals("0"));
//
//
//
//	}
//
//	private void getAll (StudentDAO studentDAO){
//		studentDAO.getAll().forEach(x-> System.out.println(x.toString()));
//	}
//
//	private void getById (StudentDAO studentDAO){
//		int id;
//
//		while (true) {
//			System.out.print("enter id : ");
//			try {
//				id = Integer.parseInt(scanner.nextLine());
//				break;
//			} catch (NumberFormatException ex) {
//				System.out.println("Please enter a valid integer.");
//			}
//		}
//
//		System.out.println(studentDAO.getById(id));
//	}
//
//	private void delete (StudentDAO studentDAO){
//		int id;
//
//		while (true) {
//			System.out.print("enter id : ");
//			try {
//				id = Integer.parseInt(scanner.nextLine());
//				break;
//			} catch (NumberFormatException ex) {
//				System.out.println("Please enter a valid integer.");
//			}
//		}
//
//		try {
//            studentDAO.delete(id);
//
//        } catch (NotFoundException e) {
//			System.out.println("Delete Failed : " + e.getMessage());
//        }
//	}
//	private void searchStudents (StudentDAO studentDAO){
//		Map<String,Object> filters = new HashMap<>();
//		System.out.println("Add filters as {Key}={Value}");
//		System.out.println("When end please press 0");
//
//		String filter ;
//		do{
//			System.out.print("Please enter the filter : ");
//			filter = scanner.nextLine();
//			if(!filter.equals("0")){
//				if(filter.contains("=")){
//					var keyValue = filter.split("=");
//					var key = keyValue[0];
//					var value = keyValue[1];
//
//					QueryBuilder.addToFilter(Student.class,filters,key,value);
//
//				}else{
//					System.out.println("Should be '=' in the filter");
//				}
//			}
//		}while (!filter.equals("0"));
//
//		var res = studentDAO.search(filters);
//		System.out.println(res.toString());
//	}
//	private void createStudent (StudentDAO studentDAO){
//		System.out.println("Creating Student object ... ");
//		System.out.print("first name :  ");
//		String firstName = scanner.nextLine();
//
//		System.out.print("last name :  ");
//		String lastName = scanner.nextLine();
//
//		System.out.print("email :  ");
//		String email = scanner.nextLine();
//
//		Student student = new Student(firstName, lastName , email);
//
//		System.out.println("Saving this Student ... ");
//		studentDAO.save(student);
//
//		System.out.println("Studene saved : " + student.toString());
//
//	}
//
//	private void updateStudent (StudentDAO studentDAO){
//		int id;
//
//		while (true) {
//			System.out.print("enter id : ");
//			try {
//				id = Integer.parseInt(scanner.nextLine());
//				break;
//			} catch (NumberFormatException ex) {
//				System.out.println("Please enter a valid integer.");
//			}
//		}
//
//		System.out.print("first name :  ");
//		String firstName = scanner.nextLine();
//
//		System.out.print("last name :  ");
//		String lastName = scanner.nextLine();
//
//		System.out.print("email :  ");
//		String email = scanner.nextLine();
//
//		Student student = new Student(firstName, lastName , email);
//		student.setId(id);
//
//		System.out.println("Saving this Student ... ");
//		try {
//			studentDAO.update(student);
//
//        }catch (NotFoundException ex){
//			System.out.println("Failed to update : " + ex.getMessage());
//		}
//
//		System.out.println("Student saved : " + student.toString());
//
//	}
}
