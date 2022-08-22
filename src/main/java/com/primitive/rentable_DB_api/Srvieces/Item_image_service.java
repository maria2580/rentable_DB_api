package com.primitive.rentable_DB_api.Srvieces;

import com.primitive.rentable_DB_api.Cotrolers.DB_Connection_Data;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
@Component
public class Item_image_service {
    DB_Connection_Data key=DB_Connection_Data.getInstance();
    String osName = System.getProperty("os.name").toLowerCase();
    private final String sep=osName.contains("win")?File.separator+File.separator:File.separator;//윈도우면 구분자 두번 리눅스면 구분자 한번
    private final String sepD=osName.contains("win")?sep+sep:sep;//윈도우면 db에 저장할 때 \\\\로 보내야 \\으로 저장되고 다시 받아 올 때 \로 값이 옴.
    private final String uploadDir = (osName.contains("win")?"D:\\rentable_images\\images":"/home/ubuntu/rentable_images/images");//sep 이 / 인 경우 리눅스이며 구분자 중복 필요 없음
    private final String DB_res = (osName.contains("win")?"D:\\\\rentable_images\\\\images":"/home/ubuntu/rentable_images/images");
    @Autowired(required = false)
    public String get_item_image(String item_index,int a) throws IOException {

        String filePath = null;//DB에서 받아온 경로가 저장될 변수
        FileInputStream in=null;//로컬에서 읽은 파일이 들어오는 변수
        //DB에서 경로 조회
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(key.getURL(), key.getDBuser(), key.getDBpw());
            con.createStatement().execute("use " + key.getDBname());

            String query = String.format("SELECT * FROM item_images where related_item_index =%d ORDER BY idx DESC LIMIT 1;",true,item_index);
            ResultSet rs =con.createStatement().executeQuery(query);
            if (rs.next()){
                filePath=rs.getString("path");
            }
            con.close();
            if (filePath==null){
                filePath=uploadDir+sep+"no_image.png";
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            in = new FileInputStream(filePath);
        }catch (Exception e){
            in = new FileInputStream(uploadDir+sep+"no_image.png");//Todo 서버 쪽에 dir이랑 no_image 미리 만들어 놓기
        }

        Base64.Encoder encoder = Base64.getEncoder();
        String encoded_image= encoder.encodeToString(IOUtils.toByteArray(in));

        return encoded_image;
    }
    @Autowired(required = false)
    public void post_item_image(int related_item_index, String[] encode_images) {

        MultipartFile[] file = null;
        Base64.Decoder decoder= Base64.getDecoder();
        try{
            for (int i=0 ; i<encode_images.length;i++){
                FileCopyUtils.copy(decoder.decode(encode_images[i]),(File)file[i]);
            }
        }catch (Exception e){e.printStackTrace();}

        long now = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD-HH-mm-ss");
        String now_date_time= sdf.format(new Date(now));
        for (int i=0;i<file.length;i++){
            if (!file[i].isEmpty()) {
                final String suffix =file[i].getOriginalFilename().substring(file[i].getOriginalFilename().lastIndexOf("."));
                String filename =related_item_index+"_"+i+"_"+suffix;
                String fullPath = uploadDir +sep+"item"+sep+ filename;
                String DBPath= DB_res+sepD+"item"+sepD+filename;

                //멀티파트로 받은 변수를 로컬 경로에 저장
                if(file[i] != null) {
                    try{
                        File uploadFile = new File(fullPath);
                        FileCopyUtils.copy(file[i].getBytes(), uploadFile);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                //저장한 경로를 DB 테이블에 저장
                try{
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(key.getURL(), key.getDBuser(), key.getDBpw());
                    con.createStatement().execute("use "+key.getDBname());

                    //마지막 인덱스값 구해옴
                    int idx;
                    ResultSet rs =con.createStatement().executeQuery("SELECT index FROM item_images ORDER BY idx DESC LIMIT 1;");
                    rs.next();
                    try{
                        idx = rs.getInt("idx");
                    }catch (Exception e) {
                        idx = 0;
                    }
                    //경로 저장
                    String query = String.format("SELECT * FROM item_images where related_item_index=%d;",true,related_item_index);
                    ResultSet rs2 =con.createStatement().executeQuery(query);
                    if(rs2.next()){
                        //DB에 값이 이미 들어있는 경우 - 수정
                        con.createStatement().execute(String.format("update item_images set Path='%s' where related_item_index='%s';",DBPath,related_item_index));//Todo 5장올린사진 -> 4장으로 변경하는등의 동작은 따로 업데이트 메서드 만들어야함
                    }else{
                        //DB에 데이터 자체가 없는 경우 - 열 추가
                        con.createStatement().execute(String.format("insert item_images (index, owners_user_id, path, uploaded_date_time) values(%d,'%s','%s','%s');",idx+1,related_item_index,DBPath,now_date_time));
                    }
                    con.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }






}
