package employeepayrollservice;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class EmployeePayrollService {

    public enum IOService{CONSOLE_IO,FILE_IO,DB_IO,REST_IO}
    private List<EmployeePayrollData> employeePayrollDataList;
    private EmployeePayrollDBService employeePayrollDBService;

    public EmployeePayrollService(){
        employeePayrollDBService = EmployeePayrollDBService.getInstance();
    }

    public EmployeePayrollService(List<EmployeePayrollData> employeePayrollDataList){
        this();
        this.employeePayrollDataList = employeePayrollDataList;
    }
    //UC-2
    public List<EmployeePayrollData> readEmployeePayrollDataDB(IOService ioService){
        if(ioService.equals(IOService.DB_IO))
            this.employeePayrollDataList = new EmployeePayrollDBService().readData();
        return this.employeePayrollDataList;
    }

    //UC-3
    public void updateEmployeeSalary(String name, double salary) {
        int result =new EmployeePayrollDBService().updateEmployeeData(name,salary);
        if(result == 0)
            return;
        EmployeePayrollData employeePayrollData = this.getEmployeePayrollData(name);
        if(employeePayrollData != null)
            employeePayrollData.salary = salary;
    }

    private EmployeePayrollData getEmployeePayrollData(String name) {
        return this.employeePayrollDataList.stream()
                .filter(employeePayrollDataItem -> employeePayrollDataItem.name.equals(name))
                .findFirst()
                .orElse(null);
    }

    public boolean checkEmployeePayrollInSyncWithDB(String name) {
        List<EmployeePayrollData> employeePayrollDataList = employeePayrollDBService.getEmployeePayrollData(name);
        return employeePayrollDataList.get(0).equals(getEmployeePayrollData(name));
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

    //UC-5
    public List<EmployeePayrollData> readEmployeePayrollDataForDateRange(IOService ioService, LocalDate startDate, LocalDate endDate) {
        if(ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getEmployeePayrollForDateRange(startDate,endDate);
        return null;
    }
    public List<EmployeePayrollData> getEmployeePayrollForDateRange(LocalDate startDate, LocalDate endDate) {
        return employeePayrollDBService.getEmployeePayrollForDateRange(startDate,endDate);
    }
    //UC-6
    public Map<String, Double> readAverageSalaryByGender(IOService ioService) {
        if(ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getAverageSalaryByGender();
        return null;
    }

    public Map<String, Double> readSalarySumByGender(IOService ioService) {
        if(ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getSalarySumByGender();
        return null;
    }

    public Map<String, Double> readMinSalaryByGender(IOService ioService) {
        if(ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getMinSalaryByGender();
        return null;
    }

    public Map<String, Double> readMaxSalaryByGender(IOService ioService) {
        if(ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getMaxSalaryByGender();
        return null;
    }

    public Map<String, Integer> readCountSalaryByGender(IOService ioService) {
        if(ioService.equals(IOService.DB_IO))
            return employeePayrollDBService.getCountSalaryByGender();
        return null;
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
