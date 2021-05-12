import employeepayrollservice.EmployeePayrollData;
import employeepayrollservice.EmployeePayrollService;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static employeepayrollservice.EmployeePayrollService.IOService.DB_IO;
import static employeepayrollservice.EmployeePayrollService.IOService.FILE_IO;

public class EmployeePayrollServiceTest {

    @Test
    public void given3Employees_WhenWrittenToFile_ShouldMatchEmployeeEntries(){
        EmployeePayrollData[] arrayOfEmps = {
                new EmployeePayrollData(1,"Jeff Bezos",100000.0),
                new EmployeePayrollData(2,"Bill Gates",200000.0),
                new EmployeePayrollData(3,"Mark Zuckerberg",300000.0)
        };
        EmployeePayrollService employeePayrollService;
        employeePayrollService = new EmployeePayrollService(Arrays.asList(arrayOfEmps));
        employeePayrollService.writeEmployeePayrollData(EmployeePayrollService.IOService.FILE_IO);
        employeePayrollService.printData(EmployeePayrollService.IOService.FILE_IO);
        long entries = employeePayrollService.countEntries(EmployeePayrollService.IOService.FILE_IO);
        Assert.assertEquals(3,entries);
    }
    //UC-2
    @Test
    public void givenEmployeePayrollInDB_whenRetrieved_shouldMatchEmployeeCount(){
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollDataDB(DB_IO);
        Assert.assertEquals(3,employeePayrollData.size());
    }

    // UC-3
    @Test
    public void givenNewSalaryForEmployee_WhenUpdated_shouldSyncDB(){
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.readEmployeePayrollDataDB(DB_IO);
        employeePayrollService.updateEmployeeSalary("Terisa",3000000);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terisa");
        Assert.assertTrue(result);

    }//UC-4
    @Test
    public void givenNewSalaryForEmployee_WhenUpdatedUsingPreparedStatement_shouldSyncDB(){
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollDataDB(DB_IO);
        employeePayrollService.updateEmployeeSalary("Terisa",3000000);
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Terisa");
        Assert.assertTrue(result);
    }
}
