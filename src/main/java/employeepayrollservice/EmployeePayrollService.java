package employeepayrollservice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollService {

    public enum IOService{CONSOLE_IO,FILE_IO,DB_IO,REST_IO}
    private List<EmployeePayrollData> employeePayrollDataList;

    public EmployeePayrollService(){}

    public EmployeePayrollService(List<EmployeePayrollData> employeePayrollDataList){
        this.employeePayrollDataList = employeePayrollDataList;
    }
    public List<EmployeePayrollData> readEmployeePayrollDataDB(IOService ioService){
        if(ioService.equals(IOService.DB_IO))
            this.employeePayrollDataList = new EmployeePayrollDBService().readData();
        return this.employeePayrollDataList;
    }

    public void readEmployeePayrollData(Scanner consoleInputReader) {
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
    public void writeEmployeePayrollData(IOService ioService) {
        if(ioService.equals(IOService.CONSOLE_IO))
            System.out.println("\nWriting Employee Payroll Roaster To console::\n"+employeePayrollDataList);
        else
            new EmployeePayrollFileIOService().writeData(employeePayrollDataList);
    }
    //method to read data on console
    public long readEmployeePayrollData(IOService ioService) {
        if(ioService.equals(IOService.FILE_IO))
            this.employeePayrollDataList = new EmployeePayrollFileIOService().readData();
        return employeePayrollDataList.size();
    }
    // method to print entries from a file
    public void printData(IOService fileIo){
        if(fileIo.equals(IOService.FILE_IO))
            new EmployeePayrollFileIOService().printData();
    }
    //method to count entries
    public long countEntries(IOService fileIo) {
        long entries = 0;
        try {
            entries = Files.lines(new File("payroll-file.txt").toPath()).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }
   //main method
    public static void main(String[] args) {
        ArrayList<EmployeePayrollData> employeePayrollList = new ArrayList<>();
        EmployeePayrollService employeePayrollService = new EmployeePayrollService(employeePayrollList);
        Scanner consoleInputReader = new Scanner(System.in);
        employeePayrollService.readEmployeePayrollData(consoleInputReader);
        employeePayrollService.writeEmployeePayrollData(IOService.FILE_IO);
    }

}
