# Gruppeo

Gruppeo is an Angular-based web application designed to energize and optimize group work. It allows users to create and manage lists of people and form groups based on various personal attributes such as technical knowledge, language skills, personality profiles, and more.

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Features

- **User Authentication**: Register, login, and manage your account
- **List Management**: Create and visualize lists of people
- **Group Management**: Create and visualize groups from your lists
- **Person Attributes**: Track various attributes for each person:
  - Gender (Man, Woman, NonBinary)
  - French language knowledge (scale 1-10)
  - Technical knowledge (scale 1-10)
  - Previous DWWM experience
  - Personality profile (shy, reserved, at ease)
  - Age
- **Responsive Design**: Works on desktop and mobile devices

## Technologies Used

- **Frontend**: Angular 19.2.0
- **Language**: TypeScript
- **Styling**: CSS
- **Routing**: Angular Router
- **Architecture**: Standalone Components (Angular 14+ approach)

## Project Structure

The project follows a standard Angular structure with standalone components:

```
gruppeo/
├── src/
│   ├── app/
│   │   ├── components/
│   │   │   ├── account/
│   │   │   ├── checkbox-choice/
│   │   │   ├── discovery/
│   │   │   ├── dropdown-selection/
│   │   │   ├── footer/
│   │   │   ├── group-creation/
│   │   │   ├── group-visualization/
│   │   │   ├── header-connected/
│   │   │   ├── header-connection/
│   │   │   ├── legal-mentions/
│   │   │   ├── list-creation/
│   │   │   ├── list-visualization/
│   │   │   ├── login/
│   │   │   ├── presentation-img/
│   │   │   ├── register/
│   │   │   ├── search-bar/
│   │   │   └── text-input/
│   │   ├── app.component.ts
│   │   ├── app.component.html
│   │   ├── app.component.css
│   │   └── app.routes.ts
│   ├── assets/
│   ├── globalTypes.ts
│   ├── globalVariables.ts
│   └── index.html
├── public/
│   └── assets/
├── package.json
└── README.md
```

## Installation

1. Ensure you have Node.js and npm installed
2. Clone the repository
3. Install dependencies:

```bash
npm install
```

## Usage

### Development Server

Run the development server:

```bash
ng serve
```

Navigate to `http://localhost:4200/` in your browser. The application will automatically reload if you change any of the source files.

### Build

Build the project for production:

```bash
ng build
```

The build artifacts will be stored in the `dist/` directory.

### Testing

Run unit tests:

```bash
ng test
```

## Design

The application follows a design system defined in Figma. The Figma link containing the graphic chart, model, and MCD (Conceptual Data Model) of the project should be added here when available.

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details.

---

*Note: This README is a work in progress. Additional information, such as the Figma link with the graphic chart, model, and MCD of the project, should be added when available.*
