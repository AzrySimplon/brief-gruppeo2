import { Routes } from '@angular/router';
import {ListVisualizationComponent} from './components/list-visualization/list-visualization.component';
import {DiscoveryComponent} from './components/discovery/discovery.component';
import {GroupVisualizationComponent} from './components/group-visualization/group-visualization.component';
import {GroupCreationComponent} from './components/group-creation/group-creation.component';
import {ListCreationComponent} from './components/list-creation/list-creation.component';
import {RegisterComponent} from './components/register/register.component';
import {LoginComponent} from './components/login/login.component';
import {AccountComponent} from './components/account/account.component';
import {LegalMentionsComponent} from './components/legal-mentions/legal-mentions.component';

export const routes: Routes = [
  {path: '', component: DiscoveryComponent},
  {path: 'lists', component: ListVisualizationComponent},
  {path: 'groups/:listId', component: GroupVisualizationComponent},
  {path: 'group-creation/:listId', component: GroupCreationComponent},
  {path: 'list-creation', component: ListCreationComponent},
  {path: 'register', component:RegisterComponent},
  {path: 'login', component:LoginComponent},
  {path: 'account', component:AccountComponent},
  {path: 'legal-mentions', component:LegalMentionsComponent}];
