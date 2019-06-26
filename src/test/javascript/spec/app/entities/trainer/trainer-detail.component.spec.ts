/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TothApplicationTestModule } from '../../../test.module';
import { TrainerDetailComponent } from 'app/entities/trainer/trainer-detail.component';
import { Trainer } from 'app/shared/model/trainer.model';

describe('Component Tests', () => {
  describe('Trainer Management Detail Component', () => {
    let comp: TrainerDetailComponent;
    let fixture: ComponentFixture<TrainerDetailComponent>;
    const route = ({ data: of({ trainer: new Trainer(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [TothApplicationTestModule],
        declarations: [TrainerDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TrainerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TrainerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.trainer).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
