## Application to support learning English using JavaFx

# Author
Group 12 - INT2204 1
1. Nguyễn Việt Cường - 22021211
2. Đào Lê Bảo Minh - 22021115
3. Nguyễn Sinh Hùng - 22021184

# Description
Ứng dụng này:
1. được thiết nhằm hỗ trợ việc học tiếng anh.
2. được viết bằng Java, C++ và sử dụng thư viện JavaFX.
3. dựa trên mô hình MVC, có sử dụng design pattern.
4. có 2 loại từ điển: Anh - Việt và Việt - Anh.
5. Ứng dụng sử dụng chuỗi hóa (.dat), database sqlite, database firebase để lưu trữ và đồng bộ dữ liệu trên nhiều thiết bị, sever.

# Installation
1. Clone project này về máy.
2. Mở project bằng IntelliJ IDEA.
3. Add các lib ở trong thư mục lib vào project.

# Usage
1. Chạy file DictionaryApplication
2. Ứng dụng đã tích hợp sẵn database có thể tìm kiếm song ngữ
3. Tìm kiếm một từ trong từ điển và chọn những từ gợi ý mà bạn muốn tra cứu khi đó ứng dụng sẽ hiển thị nghĩa của từ đó
4. Để thêm từ mới bạn hãy vào bookmark nhấn vào nút Add new word. 
5. Để xóa một từ, bạn click vào từ đó và nhấn vào nút remove.
6. Để sửa một từ, bạn click vào từ đó sửa theo ý bạn muốn
7. Để phát âm từ đó hãy nhấp vào nút phát âm (biểu tượng là loa).
8. Để luyện tập, bạn hãy vào bookmark (có 2 game là hangman và crossword).
    - Game Hangman sẽ lấy những từ mà bạn lưu vào bookmark để luyện tập
    - HangMan sẽ cho bạn nghĩa tiếng việt của từ đó
    - Sẽ có những từ và bạn cần sắp xếp chúng sao cho nó đúng nghĩa của từ đó
    - Nếu sai thì sẽ treo cổ huhuhuhu : <
    - crossWord sẽ lấy những từ mà trong database có để bạn sử dụng khả năng tìm kiếm từ của mình thành thạo hơn.

# Using In SceneBuilder
1. Mở file fxml mà bạn muốn chỉnh sửa
2. Chọn mục Controller và chọn đường dẫn đến file controller của fxml đó


# UML
![ClassUML](https://github.com/nvicuong/LearningEnglish/assets/112748853/d4f8d66f-d7fb-4cdb-92b4-04cb60e8d0f4)
# Demo
![Screenshot 2023-11-26 221035](https://github.com/nvicuong/LearningEnglish/assets/112748853/5dcfe292-0ad6-4c24-8613-0b3d927fdc03)
![Screenshot 2023-11-26 221248](https://github.com/nvicuong/LearningEnglish/assets/112748853/bc74505a-6118-4f12-888d-3b321b261fe1)
![Screenshot 2023-11-26 221306](https://github.com/nvicuong/LearningEnglish/assets/112748853/4e23c52e-567f-45bc-9289-6915bae94e46)
![Screenshot 2023-11-26 221336](https://github.com/nvicuong/LearningEnglish/assets/112748853/4a325c7d-31d3-4dea-a97a-976ccbd822f6)
![Screenshot 2023-11-26 221402](https://github.com/nvicuong/LearningEnglish/assets/112748853/13154b55-d401-468e-b215-0063952ee84e)
![Screenshot 2023-11-26 221417](https://github.com/nvicuong/LearningEnglish/assets/112748853/9f5f5a11-fb48-4026-8bf1-c85423b1e06b)
![Screenshot 2023-11-26 221442](https://github.com/nvicuong/LearningEnglish/assets/112748853/830d707b-df4c-4b1a-a74f-5ef4b2dd478b)
![Screenshot 2023-11-26 221454](https://github.com/nvicuong/LearningEnglish/assets/112748853/60bca377-2fb2-4554-a62a-113656c6afd7)
![Screenshot 2023-11-26 221507](https://github.com/nvicuong/LearningEnglish/assets/112748853/87ad306d-ab11-410b-9b3c-7e7cc78476a9)

# Future improvements
1. Thêm nhiều từ điển hơn
2. Thêm các trò chơi thú vị hay ho để cải thiện khả năng học từ vựng
3. Tối ưu hóa code
4. Cải thiện đồ họa cho nhiều cảm hứng học tập
5. Tích hợp ứng dụng với API của Google Speech to Text để chuyển lời nói thành văn bản.

# Contributing

# Project status
* Tình trạng của dự án
  Dự án đã hoàn thành.

# Notes
* Ứng dụng này được viết cho mục đích giáo dục.
