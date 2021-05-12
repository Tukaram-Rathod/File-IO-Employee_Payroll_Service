import employeepayrollservice.EmployeePayrollData;
import employeepayrollservice.EmployeePayrollService;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
    //UC-5
    @Test
    public void givenDateRange_whenRetrieved_shouldMatchEmployeeCount(){
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollDataDB(DB_IO);
        LocalDate startDate = LocalDate.of(2018,01,01);
        LocalDate endDate = LocalDate.now();
        List<EmployeePayrollData> employeePayrollData = employeePayrollService.getEmployeePayrollForDateRange(startDate,endDate);
        Assert.assertEquals(3,employeePayrollData.size());
    }
    //UC-6
    @Test
    public void givenPayrollData_whenAverageSalaryRetrievedByGender_shouldReturnProperValue(){
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollDataDB(DB_IO);

        Map<String,Double> salarySumByGender = employeePayrollService.readSalarySumByGender(DB_IO);
        Map<String,Double> averageSalaryByGender = employeePayrollService.readAverageSalaryByGender(DB_IO);
        Map<String,Double> minSalaryByGender = employeePayrollService.readMinSalaryByGender(DB_IO);
        Map<String,Double> maxSalaryByGender = employeePayrollService.readMaxSalaryByGender(DB_IO);
        Map<String,Integer> countSalaryByGender = employeePayrollService.readCountSalaryByGender(DB_IO);

        Assert.assertTrue(salarySumByGender.get("M").equals(4000000.00) && salarySumByGender.get("F").equals(3000000.00));
        Assert.assertTrue(averageSalaryByGender.get("M").equals(2000000.00) && averageSalaryByGender.get("F").equals(3000000.00));
        Assert.assertTrue(minSalaryByGender.get("M").equals(1000000.00) && minSalaryByGender.get("F").equals(3000000.00));
        Assert.assertTrue(maxSalaryByGender.get("M").equals(3000000.00) && maxSalaryByGender.get("F").equals(3000000.00));
        Assert.assertTrue(countSalaryByGender.get("M").equals(2) && countSalaryByGender.get("F").equals(1));
    }
    //UC-7
    @Test
    public void givenNewEmployee_whenAdded_shouldSyncWithDB(){
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollDataDB(DB_IO);
        employeePayrollService.readEmployeePayrollData(DB_IO);
        employeePayrollService.addEmployeeToPayroll("Mark","M", 6000000.00, LocalDate.now());
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Mark");
        Assert.assertTrue(result);
    }
    //UC-8
    @Test
    public void givenNewEmployee_whenAdded_shouldAddPayrollDetailsAndSyncWithDB(){
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        employeePayrollService.readEmployeePayrollDataDB(DB_IO);
        employeePayrollService.readEmployeePayrollData(DB_IO);
        employeePayrollService.addEmployeeToPayroll("Mark","M", 5000000.00, LocalDate.now());
        boolean result = employeePayrollService.checkEmployeePayrollInSyncWithDB("Mark");
        Assert.assertTrue(result);
    }
}
