# 🚀 Công cụ tự động Push Code lên GitHub cho HerbAI
$gitPath = "C:\Users\p\AppData\Local\GitHubDesktop\app-3.5.4\resources\app\git\cmd\git.exe"

Write-Host "--- 🛠️ Đang chuẩn bị Project... ---" -ForegroundColor Cyan

# 1. Khởi tạo Git nếu chưa có
if (!(Test-Path ".git")) {
    & $gitPath init
    Write-Host "✅ Đã khởi tạo Git Repo." -ForegroundColor Green
}

# 2. Add và Commit
Write-Host "--- 📦 Đang đóng gói code (Bỏ qua file rác)... ---" -ForegroundColor Cyan
& $gitPath add .
& $gitPath commit -m "🚀 Hoàn thiện tính năng Camera, Health Connect và Affiliate"

# 3. Hỏi link Repo
Write-Host "`n--- 🔗 Cấu hình GitHub ---" -ForegroundColor Magenta
$repoUrl = Read-Host "Dán Link Repository GitHub của bạn vào đây (VD: https://github.com/user/repo.git)"

if ($repoUrl -match "http") {
    # Xóa remote cũ nếu có và thêm mới
    & $gitPath remote remove origin 2>$null
    & $gitPath remote add origin $repoUrl
    
    Write-Host "--- 🚀 Đang đẩy code lên GitHub... ---" -ForegroundColor Cyan
    & $gitPath branch -M main
    & $gitPath push -u origin main -f
    
    Write-Host "`n✅ THÀNH CÔNG! Code đã được tải lên." -ForegroundColor Green
    Write-Host "Bây giờ bạn hãy vào tab 'Actions' trên trình duyệt để thấy bản build đang chạy." -ForegroundColor Yellow
}
else {
    Write-Host "❌ Link không hợp lệ. Vui lòng chạy lại script và dán đúng link." -ForegroundColor Red
}

Write-Host "Dừng 5 giây trước khi đóng..."
Start-Sleep -Seconds 5
