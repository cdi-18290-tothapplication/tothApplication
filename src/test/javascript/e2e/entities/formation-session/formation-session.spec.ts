/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { FormationSessionComponentsPage, FormationSessionDeleteDialog, FormationSessionUpdatePage } from './formation-session.page-object';

const expect = chai.expect;

describe('FormationSession e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let formationSessionUpdatePage: FormationSessionUpdatePage;
  let formationSessionComponentsPage: FormationSessionComponentsPage;
  let formationSessionDeleteDialog: FormationSessionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load FormationSessions', async () => {
    await navBarPage.goToEntity('formation-session');
    formationSessionComponentsPage = new FormationSessionComponentsPage();
    await browser.wait(ec.visibilityOf(formationSessionComponentsPage.title), 5000);
    expect(await formationSessionComponentsPage.getTitle()).to.eq('tothApplicationApp.formationSession.home.title');
  });

  it('should load create FormationSession page', async () => {
    await formationSessionComponentsPage.clickOnCreateButton();
    formationSessionUpdatePage = new FormationSessionUpdatePage();
    expect(await formationSessionUpdatePage.getPageTitle()).to.eq('tothApplicationApp.formationSession.home.createOrEditLabel');
    await formationSessionUpdatePage.cancel();
  });

  it('should create and save FormationSessions', async () => {
    const nbButtonsBeforeCreate = await formationSessionComponentsPage.countDeleteButtons();

    await formationSessionComponentsPage.clickOnCreateButton();
    await promise.all([
      formationSessionUpdatePage.setBeginInput('2000-12-31'),
      formationSessionUpdatePage.setEndInput('2000-12-31'),
      // formationSessionUpdatePage.studientsSelectLastOption(),
      // formationSessionUpdatePage.documentsSelectLastOption(),
      formationSessionUpdatePage.formationSelectLastOption()
    ]);
    expect(await formationSessionUpdatePage.getBeginInput()).to.eq('2000-12-31', 'Expected begin value to be equals to 2000-12-31');
    expect(await formationSessionUpdatePage.getEndInput()).to.eq('2000-12-31', 'Expected end value to be equals to 2000-12-31');
    await formationSessionUpdatePage.save();
    expect(await formationSessionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await formationSessionComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last FormationSession', async () => {
    const nbButtonsBeforeDelete = await formationSessionComponentsPage.countDeleteButtons();
    await formationSessionComponentsPage.clickOnLastDeleteButton();

    formationSessionDeleteDialog = new FormationSessionDeleteDialog();
    expect(await formationSessionDeleteDialog.getDialogTitle()).to.eq('tothApplicationApp.formationSession.delete.question');
    await formationSessionDeleteDialog.clickOnConfirmButton();

    expect(await formationSessionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
