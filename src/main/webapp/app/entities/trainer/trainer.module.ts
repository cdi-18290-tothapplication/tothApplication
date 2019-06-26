import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TothApplicationSharedModule } from 'app/shared';
import {
  TrainerComponent,
  TrainerDetailComponent,
  TrainerUpdateComponent,
  TrainerDeletePopupComponent,
  TrainerDeleteDialogComponent,
  trainerRoute,
  trainerPopupRoute
} from './';

const ENTITY_STATES = [...trainerRoute, ...trainerPopupRoute];

@NgModule({
  imports: [TothApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TrainerComponent,
    TrainerDetailComponent,
    TrainerUpdateComponent,
    TrainerDeleteDialogComponent,
    TrainerDeletePopupComponent
  ],
  entryComponents: [TrainerComponent, TrainerUpdateComponent, TrainerDeleteDialogComponent, TrainerDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TothApplicationTrainerModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
