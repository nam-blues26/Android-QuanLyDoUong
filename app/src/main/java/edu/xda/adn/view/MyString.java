package edu.xda.adn.view;

public interface MyString {

    String PRICE_FORMAT = "###,###,###";

    String INVOICE_INTENT_FORMAT = "invoice";

    String CONFIRM_TITLE = "Xác nhận!";

    String DELETE_CONFIRM_MESSAGE = "Bạn có chắc chắn muốn xóa không?";

    String DELETE_NAME_POSITIVE_BUTTON = "Xóa";

    String DELETE_NAME_NEGATIVE_BUTTON = "Hủy";

    String VALIDATE_PRICE_FORMAT = "^[0-9]+\\.*[0-9]*$";

    String VALIDATE_QUANTITY_FORMAT = "^[0-9]+$";

    String LOGOUT_MESSAGE = "Đăng xuất khỏi ứng dụng?";

    String LOGOUT_NAME_POSITIVE_BUTTON = "Đăng xuất";

    String LOGOUT_NAME_NEGATIVE_BUTTON = "Hủy";

    String DATETIME_FORMAT_DAY_FIRST = "dd/MM/yyyy HH:mm:ss";

    String DATE_FORMAT = "dd/MM/yyyy";

    String UNIT_NOTE_BARCHAR = "Tổng tiền theo ngày (VNĐ)";

    String DATETIME_FORMAT_YEAR_FIRST = "yyyy-MM-dd HH:mm:ss";

    String DELETE_ALL_CONFIRM_MESSAGE = "Bạn có chắc chắn muốn xóa toàn bộ sản phẩm không?";

    String DELETE_ALL_NAME_POSITIVE_BUTTON = "Xóa";

    String DELETE_ALL_NAME_NEGATIVE_BUTTON = "Hủy";

    String HELLO_USER = "Chào";

    String NO_DATA_TEXT = "Không có hóa đơn";

    //Sửa đường dẫn api ở đây nhé
    String LOCALHOST = "http://14.225.207.98:8000/";
//    String LOCALHOST = "http://172.20.10.2/DoUongAPI/";

    String URL_DELETE_PRODUCT = LOCALHOST + "/delete_product.php";

    String URL_INSERT_PRODUCT = LOCALHOST + "/insert_product.php";

    String URL_UPDATE_PRODUCT = LOCALHOST + "/update_product.php";

    String URL_INSERT_INVOICE_DETAIL = LOCALHOST + "/insert_invoice_detail.php";

    String URL_LOGIN =  LOCALHOST + "login.php";

    String URL_GET_PRODUCT =  LOCALHOST + "/product.php";

    String URL_GET_INVOICE =  LOCALHOST + "/invoice.php";

    String URL_GET_INVOICE_DETAIL =  LOCALHOST + "/user.php";

}
