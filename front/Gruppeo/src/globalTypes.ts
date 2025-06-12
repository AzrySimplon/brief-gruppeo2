enum Gender {
  Man,
  Woman,
  NonBinary
}

enum Profile {
  shy,
  reserved,
  at_ease
}

export {Gender, Profile};

declare global {



  interface List {
    id: number;
    name: string;
    nbr_persons: number;
    members: Person[];
  }

  interface Group {
    id: number;
    name: string;
    nbr_persons: number;
    members: Person[];
  }

  interface Person {
    id: number;
    name: string;
    gender: Gender;
    french_knowledge: number;
    old_dwwm: boolean;
    technical_knowledge: number;
    profile: Profile;
    age: number;
  }
}
