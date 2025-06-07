# 📷 PlateScanApp – Automatic License Plate Recognition for Android

**PlateScanApp** is an Android application developed in **Kotlin** using **Jetpack Compose**. It allows users to scan vehicle license plates using the camera, detect the plate location with a YOLOv5 model hosted on a remote server, and extract the plate number with on-device OCR (ML Kit). All results are stored in Firebase Firestore for future access and reporting.

---

## 🚀 Features

- 📸 **Scan vehicle plates** using the device camera.
- 🧠 **YOLOv5 plate detection** via backend hosted on Render.
- 🔤 **Local OCR (ML Kit)** for text recognition of the license plate.
- ☁️ **Firestore integration** to store plate, timestamp, location, and images.
- 📈 **Simple statistics** by day and month using MPAndroidChart.
- 🔐 **Authentication** with Google or email/password (Firebase Auth).
- 📝 **Create reports** linked to license plates with type, description, and optional image.
- 🔍 **Search by plate**, edit user profile, view scan history, and more.

---

## 🛠️ Tech Stack

| Category             | Tools / Technologies                         |
|----------------------|-----------------------------------------------|
| Language             | Kotlin                                        |
| UI                   | Jetpack Compose                              |
| Architecture         | MVVM                                          |
| Camera               | CameraX                                       |
| OCR                  | ML Kit (on-device)                            |
| Plate Detection      | YOLOv5 (served via FastAPI on Render)         |
| Authentication       | Firebase Auth                                 |
| Database             | Firebase Firestore                            |
| Charts               | MPAndroidChart                                |
| HTTP Client          | Retrofit                                      |
| Crash Reporting      | Firebase Crashlytics                          |

---

## 📱 App Flow

1. User logs in via Google or email/password.
2. Takes a picture of a vehicle (resized to 640x640).
3. The image is sent to the `/detect` endpoint on the backend.
4. Backend responds with a bounding box if a plate is found.
5. The app crops the image locally and runs OCR using ML Kit.
6. Detected plate number and metadata are saved to Firestore.
7. User can view scan history, create reports, and see stats.

---


---

## 📷 Screenshots

*(Insert screenshots here to visually showcase features)*

---

## ✅ Requirements

- Android 8.0+ (API 26+)
- Camera (≥8MP recommended)
- Internet access
- Camera and (optional) location permissions

---

## 🔗 Repositories

- 🔧 Backend & model: [PlateScanBackend](https://github.com/JL1704/PlateScanBackend)
- 📱 Android app: [PlateScanApp](https://github.com/JL1704/PlateScanApp)

---

## 👥 Authors

- José Luis Calderón Galarza

## 🤝 Créditos adicionales 
- Jesús Roberto Dávila González  
- Jared Esaú Sandoval Morales  
- Aarón Mireles Barrón  

---

## 📄 License

This project was developed for academic purposes at the Universidad Autónoma de Nuevo León, Facultad de Ciencias Físico Matemáticas. Commercial use is not allowed without permission.

---



