package employeepayrollservice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EmployeePayrollFileIOService {
    public static String PAYROLL_FILE_NAME = "payroll-file.txt";

    public void writeData(List<EmployeePayrollData> employeePayrollDataList) {
        StringBuffer empBuffer = new StringBuffer();
        employeePayrollDataList.forEach(employee-> {
            String employeeDataString = employee.toString().concat("\n");
            empBuffer.append(employeeDataString);
        });
        try{
            Files.write(Paths.get(PAYROLL_FILE_NAME),empBuffer.toString().getBytes());
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    //method to print entries in a file
    public void printData() {
        try{
            Files.lines(new File(PAYROLL_FILE_NAME).toPath())
                    .forEach(System.out::println);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //method to count entries in a file
    public long countEntries() {
        long entries = 0;
        try {
            entries = Files.lines(new File(PAYROLL_FILE_NAME).toPath()).count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entries;
    }

    //method to read employee data from file,store it in list and return list
    public List<EmployeePayrollData> readData(){
        List<EmployeePayrollData> employeePayrollDataList = new ArrayList<>();
        try {
            Files.lines(new File(PAYROLL_FILE_NAME).toPath())
                    .map(String::trim)
                    .forEach(System.out::println);
        }catch (IOException e){
            e.printStackTrace();
        }
    return employeePayrollDataList;
    }
}
