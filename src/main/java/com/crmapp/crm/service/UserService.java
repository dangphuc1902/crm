package com.crmapp.crm.service;

import com.crmapp.crm.entity.RolesEntity;
import com.crmapp.crm.entity.TasksEntity;
import com.crmapp.crm.entity.UsersEntity;
import com.crmapp.crm.repository.RolesRepository;
import com.crmapp.crm.repository.UserRespository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.nio.file.Files;

import static org.aspectj.weaver.tools.cache.SimpleCacheFactory.path;

@Service
public class UserService {
    @Autowired
    private NameParserService nameParserService;

    @Autowired
    private UserRespository userRespository;



    public List<UsersEntity> getAllUser(){
        return userRespository.findAll();
    }
    @Autowired
    private RolesRepository rolesRepository;

    public UsersEntity updateUserService(UsersEntity usersEntity){
        return userRespository.save(usersEntity);
    }

    public int getTaskUnfulfilled(UsersEntity user){

        List<TasksEntity> tasksEntities = user.getTasks();
        float quantity = 0;
        for (TasksEntity tasks : tasksEntities){
            if(tasks.getStatusEntity().getName().equals("Chưa thực hiện")){
                quantity += 1;
            }
        }
        if(quantity == 0){
            return 0;
        }else{
            return (int)(quantity/(float)tasksEntities.size()*100);
        }

    }

    public int getTaskProcessing(UsersEntity user){

        List<TasksEntity> tasksEntities = user.getTasks();
        float quantity = 0;
        for (TasksEntity tasks : tasksEntities){
            if(tasks.getStatusEntity().getName().equals("Đang thực hiện")){
                quantity += 1;
            }
        }
        if(quantity == 0){
            return 0;
        }else{
            return (int)(quantity/(float)tasksEntities.size()*100);
        }

    }

    public int getTaskCompleted(UsersEntity user){

        List<TasksEntity> tasksEntities = user.getTasks();
        float quantity = 0;
        for (TasksEntity tasks : tasksEntities){
            if(tasks.getStatusEntity().getName().equals("Đã hoàn thành")){
                quantity += 1;
            }
        }
        if(quantity == 0){
            return 0;
        }else{
            return (int)(quantity/(float)tasksEntities.size()*100);
        }

    }


    public UsersEntity getUserId(int user_id){
        // TODO Optional: Có hoặc không có cũng được. trong tất cả ngôn ngữ đều vậy
        // Lấy giá trị getRoleId
//      Kiểu dữ liệu Optional: Chứa các hàm hỗ trợ sẵn, Giúp kiểm tra giá trị có null hay không để tránh bị lỗi null dữ liệu trong quá trình xử lý.
        Optional<UsersEntity> usersEntity = userRespository.findById(user_id);
        UsersEntity dataRole = null;
//    do RolesEntity đang bị Optional bao bọc.Nên cần gọi hàm có sẵn.
//        isPresent();    nếu trả về true thì roleEntity có giá trị.
        if (usersEntity.isPresent()){
            dataRole = usersEntity.get();  // Giúp hủy Optional đi và trả về giá trị thực của biến.
        }
        return dataRole;
    }
    public UsersEntity save(UsersEntity usersEntity){
        return userRespository.save(usersEntity);
    }
    private boolean isEmailExist(String email){
        List<UsersEntity> usersList = userRespository.findAll();
        return usersList.stream().anyMatch(users -> users.getEmail().equalsIgnoreCase(email));
    }

    public int getRoleId(){
        List<RolesEntity> listRole = rolesRepository.findAll();
        int roleId = 0;
        for (RolesEntity role : listRole){
            roleId = role.getId();
        }
        return roleId;
    }
    @Value("${upload.path.user}")
    private String uploadPathUser;
    @Value("${upload.path.large}")
    private String uploadPathLarge;

    public boolean insertUser(String fullname,
                           String email,
                           String password,
                           String phoneNumber,
                          MultipartFile file,
                           RolesEntity rolesEntity){

        boolean isSuccess = false;  // biến flag kiểm tra xem có thêm user thành công hay không, mặc định là không (false)

        try {
            byte[] bytes = file.getBytes();
            Path pathUser = Paths.get(uploadPathUser + file.getOriginalFilename());
            Files.write(pathUser, bytes);
            System.out.println("Check pathUser:" + pathUser.toString() + " " + Files.write(pathUser, bytes));
            Path pathLarge = Paths.get(uploadPathLarge + file.getOriginalFilename());
            Files.write(pathLarge, bytes);
            String originalPath = pathUser.toString();
            String newPathAvata = originalPath.replace("src\\main\\resources\\static\\plugins\\images\\users\\", "/plugins/images/users/");
            System.out.println("newPath: " + newPathAvata);
            UsersEntity usersEntity = new UsersEntity();
            usersEntity.setFullname(fullname);
            String[] nameParts = nameParserService.getFirstNameAndLastName(fullname);
            usersEntity.setFirstname(nameParts[0]);
            usersEntity.setLastname(nameParts[1]);
            usersEntity.setEmail(email);
            usersEntity.setPassword(password);
            usersEntity.setPhonenumber(phoneNumber);
            usersEntity.setAvatarPath(newPathAvata);
            usersEntity.setRolesEntity(rolesEntity);
            if (!isEmailExist(email)){ // Yêu cầu khi thêm thì email phải khác nhau, nếu email không tồn tại thì mới add user.
                userRespository.save(usersEntity);
                isSuccess = true;
            }
            else {
                System.out.println(email + "đã tồn tại!");
            }
        } catch (Exception e) {
            // đoạn code bên trong catch chỉ được chạy khi đoạn code bên trong try bị lỗi (Runtime Error)
            e.printStackTrace();
        }
        return isSuccess;
    }

    public void deleteSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

    }
    public void deleteUser(int user_Id){
        userRespository.deleteById(user_Id);
    }
    public UsersEntity getUserBySession(HttpSession session) {
        UsersEntity usersEntity = null;
        String email = "";
        if(session != null && session.getAttribute("email")!= null && !session.getAttribute("email").equals("")){
            email = (String) session.getAttribute("email");
            usersEntity = userRespository.getByEmail(email);
            System.out.println("Kiêm tra " + email);
        } else {
            System.out.println("Không thấy email");
        }
        return usersEntity;
    }
    public List<TasksEntity> checkTasksUnfulfilled(List<TasksEntity> task){
        return task.stream()
                .filter(tasks -> tasks.getStatusEntity().getName().equals("Chưa thực hiện")).toList();
    }
    public List<UsersEntity> getUserForUpdate(HttpSession session){
        UsersEntity users = getUserBySession(session);
        if(users.getRolesEntity().getName().equals("ROLE_USER")){
            return Collections.singletonList(users);
        }else return userRespository.findAll();
    }
    public List<TasksEntity> checkTasksProcessing(List<TasksEntity> task){
        return task.stream()
                .filter(tasks -> tasks.getStatusEntity().getName().equals("Đang thực hiện")).toList();
    }

    public List<TasksEntity> checkTasksMade(List<TasksEntity> task){
        return task.stream()
                .filter(tasks -> tasks.getStatusEntity().getName().equals("Đã hoàn thành")).toList();
    }


    public int getTaskUnfulfilled(List<TasksEntity> task){

        float quantity = 0;
        for (TasksEntity tasks : task){
            if(tasks.getStatusEntity().getName().equals("Chưa thực hiện")){
                quantity += 1;
            }
        }
        if(quantity == 0){
            return 0;
        }else{
            return (int)(quantity/(float)task.size()*100);
        }

    }

    public int getTaskProcessing(List<TasksEntity> task){


        float quantity = 0;
        for (TasksEntity tasks : task){
            if(tasks.getStatusEntity().getName().equals("Đang thực hiện")){
                quantity += 1;
            }
        }
        if(quantity == 0){
            return 0;
        }else{
            return (int)(quantity/(float)task.size()*100);
        }

    }

    public int getTaskCompleted(List<TasksEntity> task){

        float quantity = 0;
        for (TasksEntity tasks : task){
            if(tasks.getStatusEntity().getName().equals("Đã hoàn thành")){
                quantity += 1;
            }
        }
        if(quantity == 0){
            return 0;
        }else{
            return (int)(quantity/(float)task.size()*100);
        }

    }
    private boolean checkForNull (String fullName, String email, String password, String phoneNo){

        return fullName != null && !fullName.isEmpty()
                && email != null && !email.isEmpty()
                && password != null && !password.isEmpty()
                && phoneNo != null && !phoneNo.isEmpty();
    }
    public String getPathAvatar(MultipartFile file){
        String newPath = null;
        try {
            byte[] bytes = file.getBytes();
            Path pathUser = Paths.get(uploadPathUser + file.getOriginalFilename());
            Files.write(pathUser, bytes);
            Path pathLarge = Paths.get(uploadPathLarge + file.getOriginalFilename());
            Files.write(pathLarge, bytes);
            String originalPath = pathUser.toString();
            System.out.println("Kiểm tra originalPath:  " + originalPath);
            String avataPath = originalPath.replace("src\\main\\resources\\static\\plugins\\images\\users\\", "/plugins/images/users/");
            newPath = avataPath;
            System.out.println("Check : " + newPath);
        } catch (Exception e){
            e.printStackTrace();
        }
        return newPath;
    }
    public boolean processUpdate(String fullname,
                              String email,
                              String password,
                              int userid,
                              String phoneNumber,
                             MultipartFile file,
                              RolesEntity rolesEntity){
        boolean isSuccess = false;  // biến flag kiểm tra xem có thêm user thành công hay không, mặc định là không (false)
        String avataPath = getPathAvatar(file);
        UsersEntity usersEntity = new UsersEntity();
        UsersEntity getUserId = getUserId(userid);
//        System.out.println("Kiểm tra avata Path: " + ((avataPath == null) ? getUserId.getAvatarPath() : avataPath));
        usersEntity.setId(userid);
        usersEntity.setFullname(fullname);
        String[] nameParts = nameParserService.getFirstNameAndLastName(fullname);
        usersEntity.setFirstname(nameParts[0]);
        usersEntity.setLastname(nameParts[1]);
        usersEntity.setEmail(email);
        usersEntity.setPassword(password);
        usersEntity.setPhonenumber(phoneNumber);
        usersEntity.setRolesEntity(rolesEntity);
        usersEntity.setAvatarPath((avataPath == null) ? getUserId.getAvatarPath() : avataPath);

        try {
            if (checkForNull(fullname, email, password, phoneNumber) && (email.equalsIgnoreCase(getUserId.getEmail()) || !isEmailExist(email))){
                userRespository.save(usersEntity);
                System.out.println();
                isSuccess = true;
            }
            else {
                System.out.println(email + " đã tồn tại!");
            }
        } catch (Exception e) {
            // đoạn code bên trong catch chỉ được chạy khi đoạn code bên trong try bị lỗi (Runtime Error)
            e.printStackTrace();
        }
        return isSuccess;
    }
    public String getPathAvata(UsersEntity users){
        String avatarPath = users.getAvatarPath();
        if(avatarPath != null){
            return avatarPath;
        } else return "/plugins/images/users/avatadefault.jpg";
    }
}
