# 🚀 Công cụ tự động Đẩy Code lên GitHub (HerbAI)
$gitPath = "C:\Users\p\AppData\Local\GitHubDesktop\app-3.5.4\resources\app\git\cmd\git.exe"

Write-Host "--- 📦 Code đã được tôi chuẩn bị sẵn sàng trên máy bạn! ---" -ForegroundColor Green

# Hỏi link Repo
Write-Host "`n--- 🔗 KẾT NỐI VỚI GITHUB ---" -ForegroundColor Magenta
$repoUrl = Read-Host "Dán Link Repository GitHub của bạn vào đây (VD: https://github.com/user/repo.git)"

if ($repoUrl -match "http") {
    Write-Host "--- 🚀 Đang đẩy code lên... ---" -ForegroundColor Cyan
    
    # Cấu hình remote và đẩy
    & $gitPath remote remove origin 2>$null
    & $gitPath remote add origin $repoUrl
    & $gitPath branch -M main
    
    Write-Host "🔔 Một cửa sổ đăng nhập GitHub có thể hiện ra, bạn hãy đăng nhập để hoàn tất nhé!" -ForegroundColor Yellow
    & $gitPath push -u origin main -f
    
    if ($LASTEXITCODE -eq 0) {
        Write-Host "`n✅ THÀNH CÔNG! Code đã được tải lên GitHub." -ForegroundColor Green
        Write-Host "Bạn có thể vào tab 'Actions' trên trình duyệt để lấy App." -ForegroundColor Yellow
    }
    else {
        Write-Host "`n❌ Có lỗi xảy ra khi đẩy code. Vui lòng kiểm tra lại quyền truy cập hoặc link Repo." -ForegroundColor Red
    }
}
else {
    Write-Host "❌ Link không hợp lệ." -ForegroundColor Red
}

Write-Host "`nDừng 10 giây trước khi đóng..."
Start-Sleep -Seconds 10
