/// <reference path="./globalTypes.ts" />
import {Gender, Profile} from "./globalTypes";

function generateRandomPerson() : Person{
  return(
    {
      id: Math.floor(Math.random() * 1000),
      name: `Person${Math.floor(Math.random() * 100)}`,
      gender: Math.random() < 0.33 ? Gender.Man : Math.random() < 0.66 ? Gender.Woman : Gender.NonBinary,
      french_knowledge: Math.floor(Math.random() * 10) + 1,
      old_dwwm: Math.random() < 0.5,
      technical_knowledge: Math.floor(Math.random() * 10) + 1,
      profile: Math.random() < 0.33 ? Profile.shy : Math.random() < 0.66 ? Profile.reserved : Profile.at_ease,
      age: Math.floor(Math.random() * 40) + 20
    }
  )

}


const member1: Person = generateRandomPerson();
const member2: Person = generateRandomPerson();
const member3: Person = generateRandomPerson();
const member4: Person = generateRandomPerson();


export class GlobalVariables {
  public static user = {
    isConnected: false
  }

  public static temporary: {lists: List[], groups: Group[]} = {
    lists:[{id: 1, name: "liste1", nbr_persons: 5, members: [member1, member2]}, {id: 2, name: "liste recherche stage", nbr_persons: 3, members: [member3, member4]}],
    groups: [{id: 1, name: "groupeA", nbr_persons: 2, members: [member1, member2]}, {id: 1, name: "groupe Mathis Christopher", nbr_persons: 1, members: [member3]}]
  }
}
