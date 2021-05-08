import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollService {
    private List<EmployeePayrollData> employeePayrollDataList;

    public EmployeePayrollService(List<EmployeePayrollData> employeePayrollDataList){
        this.employeePayrollDataList = employeePayrollDataList;
    }

    public static void main(String[] args) {
        ArrayList<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollList);
        Scanner consoleInputReader = new Scanner(System.in);
        employeePayrollService.readEmployeePayrollData(consoleInputReader);
        employeePayrollService.writeEmployeePayrollData();
    }
    //method to read data
    private void readEmployeePayrollData(Scanner consoleInputReader) {
        System.out.println("Enter Employee Id:");
        int id=consoleInputReader.nextInt();
        System.out.println("Enter Employee name:");
        consoleInputReader.nextLine();
        String name=consoleInputReader.nextLine();
        System.out.println("Enter Employee salary:");
        double salary=consoleInputReader.nextInt();
        employeePayrollDataList.add(new EmployeePayrollData(id,name,salary));
    }
    //method to write data on console
    private void writeEmployeePayrollData() {
        System.out.println("\nWriting Employee Payroll Roaster To console::\n"+employeePayrollDataList);
    }
}
