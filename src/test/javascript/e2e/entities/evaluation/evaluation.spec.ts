/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EvaluationComponentsPage, EvaluationDeleteDialog, EvaluationUpdatePage } from './evaluation.page-object';

const expect = chai.expect;

describe('Evaluation e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let evaluationUpdatePage: EvaluationUpdatePage;
  let evaluationComponentsPage: EvaluationComponentsPage;
  let evaluationDeleteDialog: EvaluationDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Evaluations', async () => {
    await navBarPage.goToEntity('evaluation');
    evaluationComponentsPage = new EvaluationComponentsPage();
    await browser.wait(ec.visibilityOf(evaluationComponentsPage.title), 5000);
    expect(await evaluationComponentsPage.getTitle()).to.eq('tothApplicationApp.evaluation.home.title');
  });

  it('should load create Evaluation page', async () => {
    await evaluationComponentsPage.clickOnCreateButton();
    evaluationUpdatePage = new EvaluationUpdatePage();
    expect(await evaluationUpdatePage.getPageTitle()).to.eq('tothApplicationApp.evaluation.home.createOrEditLabel');
    await evaluationUpdatePage.cancel();
  });

  it('should create and save Evaluations', async () => {
    const nbButtonsBeforeCreate = await evaluationComponentsPage.countDeleteButtons();

    await evaluationComponentsPage.clickOnCreateButton();
    await promise.all([
      evaluationUpdatePage.setDateInput('2000-12-31'),
      evaluationUpdatePage.setNoteInput('5'),
      evaluationUpdatePage.setCommentaryInput('commentary'),
      evaluationUpdatePage.studientSelectLastOption(),
      evaluationUpdatePage.trainerSelectLastOption(),
      evaluationUpdatePage.formationSessionSelectLastOption()
    ]);
    expect(await evaluationUpdatePage.getDateInput()).to.eq('2000-12-31', 'Expected date value to be equals to 2000-12-31');
    expect(await evaluationUpdatePage.getNoteInput()).to.eq('5', 'Expected note value to be equals to 5');
    expect(await evaluationUpdatePage.getCommentaryInput()).to.eq('commentary', 'Expected Commentary value to be equals to commentary');
    await evaluationUpdatePage.save();
    expect(await evaluationUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await evaluationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Evaluation', async () => {
    const nbButtonsBeforeDelete = await evaluationComponentsPage.countDeleteButtons();
    await evaluationComponentsPage.clickOnLastDeleteButton();

    evaluationDeleteDialog = new EvaluationDeleteDialog();
    expect(await evaluationDeleteDialog.getDialogTitle()).to.eq('tothApplicationApp.evaluation.delete.question');
    await evaluationDeleteDialog.clickOnConfirmButton();

    expect(await evaluationComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
