import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'document',
        loadChildren: './document/document.module#TothApplicationDocumentModule'
      },
      {
        path: 'ccp',
        loadChildren: './ccp/ccp.module#TothApplicationCCPModule'
      },
      {
        path: 'studient',
        loadChildren: './studient/studient.module#TothApplicationStudientModule'
      },
      {
        path: 'trainer',
        loadChildren: './trainer/trainer.module#TothApplicationTrainerModule'
      },
      {
        path: 'formation',
        loadChildren: './formation/formation.module#TothApplicationFormationModule'
      },
      {
        path: 'intervention',
        loadChildren: './intervention/intervention.module#TothApplicationInterventionModule'
      },
      {
        path: 'formation-session',
        loadChildren: './formation-session/formation-session.module#TothApplicationFormationSessionModule'
      },
      {
        path: 'evaluation',
        loadChildren: './evaluation/evaluation.module#TothApplicationEvaluationModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TothApplicationEntityModule {}
