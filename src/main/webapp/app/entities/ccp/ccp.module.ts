import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { TothApplicationSharedModule } from 'app/shared';
import {
  CCPComponent,
  CCPDetailComponent,
  CCPUpdateComponent,
  CCPDeletePopupComponent,
  CCPDeleteDialogComponent,
  cCPRoute,
  cCPPopupRoute
} from './';

const ENTITY_STATES = [...cCPRoute, ...cCPPopupRoute];

@NgModule({
  imports: [TothApplicationSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [CCPComponent, CCPDetailComponent, CCPUpdateComponent, CCPDeleteDialogComponent, CCPDeletePopupComponent],
  entryComponents: [CCPComponent, CCPUpdateComponent, CCPDeleteDialogComponent, CCPDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TothApplicationCCPModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
