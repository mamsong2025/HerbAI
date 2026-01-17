# 🚀 Công cụ tự động tạo GitHub Secrets cho HerbAI
# File này giúp bạn tạo Keystore và mã hóa nó để dán vào GitHub.

$keyName = "herbai-release-key"
$keystoreFile = "app/release.jks"
$password = "HerbAI2026@" # Bạn có thể đổi nếu muốn
$alias = "herbai-key"

Write-Host "--- 🛠️ Đang kiểm tra keytool... ---" -ForegroundColor Cyan
if (!(Get-Command keytool -ErrorAction SilentlyContinue)) {
    Write-Host "❌ LỖI: Không tìm thấy 'keytool'. Hãy chắc chắn bạn đã cài JDK và thêm vào PATH." -ForegroundColor Red
    return
}

Write-Host "--- 🔑 1. Đang tạo Keystore... ---" -ForegroundColor Cyan
if (Test-Path $keystoreFile) {
    Write-Host "⚠️ File $keystoreFile đã tồn tại. Đang bỏ qua bước tạo mới." -ForegroundColor Yellow
} else {
    keytool -genkey -v -keystore $keystoreFile -keyalg RSA -keysize 2048 -validity 10000 `
      -alias $alias -storepass $password -keypass $password `
      -dname "CN=HerbAI, OU=Dev, O=WiseMed, L=Hanoi, S=HN, C=VN"
    Write-Host "✅ Đã tạo file keystore tại: $keystoreFile" -ForegroundColor Green
}

Write-Host "`n--- 📋 2. ĐÂY LÀ CÁC THÔNG TIN BẠN CẦN DÁN VÀO GITHUB SECRETS ---`n" -ForegroundColor Magenta

# Chuyển đổi Keystore sang Base64
$fileBytes = [System.IO.File]::ReadAllBytes((Get-Item $keystoreFile).FullName)
$base64String = [System.Convert]::ToBase64String($fileBytes)

Write-Host "🎁 Tên Secret: SIGNING_KEY" -ForegroundColor Yellow
Write-Host "👉 Giá trị (Dán hết đoạn mã dưới đây):"
Write-Host $base64String
Write-Host "------------------------------------------------"

Write-Host "🎁 Tên Secret: KEY_STORE_PASSWORD" -ForegroundColor Yellow
Write-Host "👉 Giá trị: $password"
Write-Host "------------------------------------------------"

Write-Host "🎁 Tên Secret: ALIAS" -ForegroundColor Yellow
Write-Host "👉 Giá trị: $alias"
Write-Host "------------------------------------------------"

Write-Host "🎁 Tên Secret: KEY_PASSWORD" -ForegroundColor Yellow
Write-Host "👉 Giá trị: $password"
Write-Host "------------------------------------------------"

Write-Host "`n🚀 Xong! Hãy copy 4 mục trên vào GitHub Settings -> Secrets -> Actions." -ForegroundColor Green
Write-Host "⚠️ LƯU Ý: Tuyệt đối không để lộ đoạn mã SIGNING_KEY cho người khác." -ForegroundColor Red
