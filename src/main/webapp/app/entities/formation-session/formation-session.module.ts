import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TothApplicationSharedModule } from 'app/shared';
import {
  FormationSessionComponent,
  FormationSessionDetailComponent,
  FormationSessionUpdateComponent,
  FormationSessionDeletePopupComponent,
  FormationSessionDeleteDialogComponent,
  formationSessionRoute,
  formationSessionPopupRoute
} from './';

const ENTITY_STATES = [...formationSessionRoute, ...formationSessionPopupRoute];

@NgModule({
  imports: [TothApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FormationSessionComponent,
    FormationSessionDetailComponent,
    FormationSessionUpdateComponent,
    FormationSessionDeleteDialogComponent,
    FormationSessionDeletePopupComponent
  ],
  entryComponents: [
    FormationSessionComponent,
    FormationSessionUpdateComponent,
    FormationSessionDeleteDialogComponent,
    FormationSessionDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TothApplicationFormationSessionModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
