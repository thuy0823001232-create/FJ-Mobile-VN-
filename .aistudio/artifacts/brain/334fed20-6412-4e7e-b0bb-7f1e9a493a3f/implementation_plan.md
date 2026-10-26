# FJ Mobile VN - Super App Architectural Blueprint

FJ Mobile VN is a multi-feature utility and social publishing suite built in Kotlin and Jetpack Compose with a vibrant rainbow aesthetic. It integrates an animated walking AI assistant that expands into an interactive numeric PIN widget, device performance & CPU cooler tools, Bluetooth microphone audio telemetry, a multi-channel video distribution engine (YouTube, TikTok, Facebook), a C++/Java/Kotlin native bridge guide, Google Mail sync, and a mandatory first-launch onboarding flow with CAPTCHA verification.

## User Review & Critical Decisions

> [!IMPORTANT]
> The user confirmed "Cả 3" to apply all recommended configurations across the floating AI widget, rainbow visual theme, and complete onboarding sequence.

- **App Identity**: App name set to `FJ Mobile VN` in `metadata.json`, `strings.xml`, and launcher labels.
- **Loading & Onboarding**: Initial splash displays `"😘FJ Mobile VN"`, leading into mandatory onboarding requiring Google email setup, avatar upload, intro/bio, numeric password input, and an interactive robot verification challenge before entering the dashboard.
- **Walking AI & PIN Widget**: An animated walking AI assistant floating on screen that transforms on tap into an interactive floating widget allowing numeric PIN password unlock and quick system toggles.
- **Rainbow Visual Styling**: Dynamic flowing rainbow gradient brushes (`#FF0055`, `#FF9900`, `#33FF00`, `#00FFFF`, `#0066FF`, `#9900FF`) across app bars, borders, cards, and accent buttons.
- **Device Performance & CPU Cooler**: Device cooling interface simulating CPU thermal throttling relief, RAM optimization, frame rate smoothing, and background process management.
- **Bluetooth Microphone Audio Studio**: Bluetooth device discovery, wireless microphone pairing, live audio waveform visualizer, and recording controls.
- **Multi-Platform Video Publishing**: File selector to import videos, caption/metadata editor, and auto-publishing pipeline distributing content simultaneously to YouTube, TikTok, and Facebook.
- **Native Interop (C++ / Java / Kotlin)**: Interactive Native SDK Architecture Explorer explaining and demonstrating JNI bridging between C++ native libraries, Java runtime, and modern Kotlin coroutines.
- **Google Mail Connection**: Gmail account link simulator with OAuth token display, mailbox sync status, and email notification forwarding.

---

## 1. Overview & Core Concept

- **What It Does**: Unifies phone optimization, wireless mic streaming, multi-platform content distribution, and account security within an engaging rainbow-themed super app with an interactive AI companion.
- **Target Audience**: Content creators, mobile gamers, and power users who need fast video publishing and device tuning.
- **Key Value**: Replaces multiple disconnected apps by combining device telemetry, social media automation, and interactive AI security in one place.

---

## 2. User Experience & Visual Design

### User Flows
1. **First-Launch Splash & Mandatory Onboarding**:
   - Splash displays vibrant animated rainbow banner with `"😘FJ Mobile VN"`.
   - Onboarding steps:
     1. Google Email entry and profile configuration.
     2. Avatar photo selection from gallery or presets.
     3. User Bio / Introduction.
     4. Numeric PIN password creation (keypad input).
     5. Interactive "I am not a robot" CAPTCHA security puzzle.
2. **Dashboard & Walking AI Widget**:
   - Floating walking AI character floats over the interface.
   - Tapping the AI expands it into an interactive PIN Widget with numeric input to lock/unlock private features.
   - Live CPU thermal gauge, RAM meter, and "Cool Down Now" action.
3. **Bluetooth Microphone Studio**:
   - Scan for Bluetooth mics, connect headset/lapel mics, and monitor live decibel sound waves.
4. **Video Pipeline (YouTube, TikTok, Facebook)**:
   - Select video file, write caption, choose hashtags, and schedule/publish automatically across all 3 channels.
5. **C++ / Java / Kotlin SDK Bridge**:
   - Interactive JNI architecture view showing code snippets and execution paths connecting C++, Java, and Kotlin.
6. **Google Mail Integration**:
   - Connected email status, sync interval, and quick notification preview.

### Visual Identity & Theme
- **Theme Name**: *Rainbow Neon Cyber*
- **Palette**: Flowing spectrum gradient: Crimson Red (`#FF1E56`), Radiant Orange (`#FF7300`), Electric Lime (`#00E676`), Cyan (`#00F5D4`), Deep Royal (`#3A86FF`), and Neon Violet (`#7209B7`) set against an ultra-dark slate background (`#0A0E17`).
- **Typography**: Clean, bold headings with numeric monospace inputs for the PIN widget.
- **Components**: Glowing rainbow card borders, animated progress indicators, and smooth state transitions.

---

## 3. Key Product Decisions & Trade-Offs

- **Decision 1: Walking AI Animation & Transformable PIN Widget**
  - *Chosen Approach*: Implement a custom Compose overlay with walking animation loop that morphs into an interactive floating PIN Widget when clicked.
  - *Why*: Provides playful personality while fulfilling the numeric PIN security requirement without interrupting navigation.
- **Decision 2: Realistic CPU & Thermal Smoothing Simulation**
  - *Chosen Approach*: Query Android system battery/thermal metrics where accessible, combined with real garbage collection memory trims and simulated CPU throttle reduction.
  - *Why*: Delivers an immediate, responsive cooling and smoothing effect without requiring root permissions.
- **Decision 3: Multi-Platform Video Automation (YouTube, TikTok, Facebook)**
  - *Chosen Approach*: Unified multi-stage publishing pipeline with queueing, transcoding, platform-specific formatting, and live status feedback.
  - *Why*: Allows creators to publish once and distribute to all major networks seamlessly.

---

## 4. Technical Architecture & Component Hierarchy

```
┌────────────────────────────────────────────────────────┐
│                      MainActivity                      │
│      (Theme: RainbowColorScheme, Edge-to-Edge)         │
└──────────────────────────┬─────────────────────────────┘
                           │
 ┌─────────────────────────┴─────────────────────────────┐
 │                Walking AI & PIN Overlay                │
 │    (Floating Walking Avatar <-> PIN Numeric Widget)   │
 └─────────────────────────┬─────────────────────────────┘
                           │
 ┌─────────────────────────┴─────────────────────────────┐
 │                     App Content                       │
 ├──────────────────────────┬────────────────────────────┤
 │  Mandatory Onboarding    │     Main Bottom Nav        │
 │  - Splash "😘FJ Mobile"  │     - Dashboard & Cooler   │
 │  - Google Email Link     │     - Media & Auto-Upload  │
 │  - Avatar & Intro Bio    │     - Bluetooth Mic Studio │
 │  - Numeric PIN Password  │     - C++/Java SDK Bridge  │
 │  - Robot CAPTCHA Gate    │     - Google Mail Settings │
 └──────────────────────────┴────────────────────────────┘
                           │
 ┌─────────────────────────┴─────────────────────────────┐
 │                    NexusViewModel                     │
 │  - Pin & Security State  - CPU Cooling Controller     │
 │  - Media Upload Pipeline - Bluetooth Mic Stream       │
 │  - Native SDK Explorer   - Room Database Repository   │
 └───────────────────────────────────────────────────────┘
```

### Data Persistence
- **Room Database**:
  - `ProfileEntity`: User email, avatar, bio, hashed PIN password, onboarding completed flag.
  - `CoolingHistoryEntity`: Timestamp, temperature before/after, memory freed.
  - `VideoCampaignEntity`: Video path, platforms (YouTube, TikTok, Facebook), publish status.
  - `BluetoothDeviceEntity`: Paired microphone names and audio levels.
