# 🍽️ Tips App - Android Application

**Tips App** is an Android app designed to help restaurants calculate and manage shift tips efficiently and fairly. It allows staff to input working hours, job roles, and total tips (cash and credit), and automatically distributes earnings based on hours worked.

---

## 🎥 Demo & Resources

- 📽️ [App Walkthrough (YouTube)](https://youtu.be/TyvmSYpSY5I)  
- 🛠️ [Development Overview](https://youtu.be/fpwIUwqsQpw)  
- 🧠 [Presentation: Problem & Solution](https://docs.google.com/presentation/d/1OtfGs2Y-6Umi3E02-6eNb1Q6EYm9In24aaRqAL-dETA/edit?usp=sharing)

---

## 🛠 Features

### ✅ Shift Management:
- Add workers by name, job type, and shift hours.
- Support for start/end time or manual hour entry.
- Worker roles: Bartender, Waiter, Host, Manager, Dishwasher, etc.
- Converts time (e.g. 8:30 ➜ 8.5) for calculation purposes.
- Calculates hourly-based tip division for relevant staff.
- Handles both **cash** and **credit card** tips.
- Automatically deducts 6% for tax (if required).
- Total summary displayed after each shift.
- Saves shifts to Firebase Firestore.

### 📊 Shift History (PdfTableActivity):
- Loads historical shift data from Firebase Firestore.
- Displays shifts in a RecyclerView using a custom `MyAdapter`.
- Supports display of all shift-related info (hours, tips, roles).
- (Planned/Optional) Export summary to a PDF file using `PdfDocument`.

---

## 🧱 Architecture

| Class | Description |
|-------|-------------|
| `AppManager` | Core logic for managing workers, shifts, calculations, and UI updates. |
| `AppData` | Stores shift state in memory: workers list, total hours, financials. |
| `CalcTipsActivity` | UI for entering workers, hours, and roles. |
| `CalcTipsSecondActivity` | Shows shift calculation results. |
| `PdfTableActivity` | Loads previous shifts from Firebase Firestore and displays them. |
| `Worker`, `Shift` | Data model classes representing a worker and a full shift. |
| `MyAdapter`, `MyCustomAdapter` | RecyclerView/ListView adapters for displaying data. |

---

## 🔧 Technologies Used

- Java (Android SDK)
- Firebase:
  - **Authentication** – for user login/session (optional/available).
  - **Firestore** – to store and retrieve shifts.
  - **Storage** (planned) – to store PDF documents.
- UI Components:
  - `RecyclerView`, `ListView`
  - `TimePickerDialog`
  - `PdfDocument` (Android PDF API)
  - `Canvas`, `Paint` – for drawing PDFs
  - `Toast`, `Intent`, `Spinner`

---

## 🧪 How It Works

1. Add workers with time and job role.
2. The app calculates the total number of hours.
3. Enter total tips received in cash and credit.
4. Tips are divided based on hours and job type.
5. Summary is displayed and can be saved to Firebase.
6. Previous shifts are displayed in a RecyclerView for reference.

---

## 📦 Setup Instructions

### Requirements:
- Android Studio (Arctic Fox or newer recommended)
- Firebase Project with:
  - Firestore enabled
  - Authentication (optional)
  - Firebase Storage (optional)
- Add `google-services.json` to your project

