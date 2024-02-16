# Tính năng
1. Nếu như đã đăng nhập rồi thì không cần đăng nhập lại đá về trang chủ không cho vô trang đăng nhập lại.
    - B1: Khi đăng nhập thành công thì phải lưu lại thông tin user đã đăng nhập (Session/Cookie).
    - B2: Khi người dùng vào lại trang login thì kiểm tra xem Session/Cookie lưu trữ thông tin người dùng có đang tồn tại không.
    - B3: Nếu tồn tại, Chuyển hướng về trang chủ.
    - B4: Nếu không tồn tại thì cho đi vào trang login.

2. Hãy làm tính năng phân quyền cho hệ thống CRM:
   - Nếu người dùng gọi link /role nếu có quyền Admin thì mới cho vào.
   - Còn không có quyền ADMIN thì chuyển về trang 404.html
- Các trường hợp: 
  - Đăng nhập thành công mà không có quyền thì đá vào dashboard
  - Chưa đăng nhập hệ thống thì đá về login.
        
- 
3. Nếu vào link là /role mà chưa đăng nhập thi se chuyển về trang đăng nhập.
    - B1: Khi người dùng vào  trang role thì kiểm tra xem Session/Cookie lưu trữ thông tin người dùng có đang tồn tại không.
    - B2: Nếu tồn tại,cho đi vào trang role.
    - B3: Nếu không tồn tại thì Chuyển hướng về trang login.
4. Role Table
   - Yêu cầu lấy toàn bộ danh sách role và hiện thị lên giao diện role-table.html
5. Chức năng: Thêm mới thành viên
   - 
# NOTE:
1. Package chính:
   - Controller: Chỉ dùng để khai báo đường dẫn và nhận tham số của người dùng truyền lên(Không xử lý logic code ở đây)
   - Service: Chịu trách nhiệm xử lý logic code cho controller.
   - Repository: Nơi định nghĩa các câu truy vấn liên quan tới database và trả kết quả của câu truy vấn cho Service không xử lý logic code.
- Controller gọi Service gọi Repository
- Repository trả kq Service trả kq Controller