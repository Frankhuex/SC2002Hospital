package org.sc2002.hospital.container.user;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.sc2002.hospital.record.user.Staff;

public class StaffContainer extends UserContainer {
    public StaffContainer() {
        super();
    }
    public StaffContainer(String filePath) {
        super();
        try (
            FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fis)
        ) {  
            Sheet sheet = workbook.getSheetAt(0);  // 获取第一个工作表
            for (int i=1;i<=sheet.getLastRowNum();i++) {
                Row row=sheet.getRow(i);
                String hospitalId=row.getCell(0).getStringCellValue();
                String name=row.getCell(1).getStringCellValue();
                String userType=row.getCell(2).getStringCellValue();
                String gender=row.getCell(3).getStringCellValue();
                int age=(int)(row.getCell(4).getNumericCellValue());
                Staff staff=new Staff(
                    hospitalId,
                    "password",
                    name,
                    gender,
                    userType,
                    "",
                    age
                );
                putRecord(staff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getHospitalIdByRecordId(int recordId) {
        return ((Staff)(getRecord(recordId))).getHospitalId();
    }
}
