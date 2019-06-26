import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TothApplicationSharedModule } from 'app/shared';
import {
  FormationComponent,
  FormationDetailComponent,
  FormationUpdateComponent,
  FormationDeletePopupComponent,
  FormationDeleteDialogComponent,
  formationRoute,
  formationPopupRoute
} from './';

const ENTITY_STATES = [...formationRoute, ...formationPopupRoute];

@NgModule({
  imports: [TothApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    FormationComponent,
    FormationDetailComponent,
    FormationUpdateComponent,
    FormationDeleteDialogComponent,
    FormationDeletePopupComponent
  ],
  entryComponents: [FormationComponent, FormationUpdateComponent, FormationDeleteDialogComponent, FormationDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TothApplicationFormationModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
