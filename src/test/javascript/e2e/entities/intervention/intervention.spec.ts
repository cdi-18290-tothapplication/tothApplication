/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { InterventionComponentsPage, InterventionDeleteDialog, InterventionUpdatePage } from './intervention.page-object';

const expect = chai.expect;

describe('Intervention e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let interventionUpdatePage: InterventionUpdatePage;
  let interventionComponentsPage: InterventionComponentsPage;
  let interventionDeleteDialog: InterventionDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Interventions', async () => {
    await navBarPage.goToEntity('intervention');
    interventionComponentsPage = new InterventionComponentsPage();
    await browser.wait(ec.visibilityOf(interventionComponentsPage.title), 5000);
    expect(await interventionComponentsPage.getTitle()).to.eq('tothApplicationApp.intervention.home.title');
  });

  it('should load create Intervention page', async () => {
    await interventionComponentsPage.clickOnCreateButton();
    interventionUpdatePage = new InterventionUpdatePage();
    expect(await interventionUpdatePage.getPageTitle()).to.eq('tothApplicationApp.intervention.home.createOrEditLabel');
    await interventionUpdatePage.cancel();
  });

  it('should create and save Interventions', async () => {
    const nbButtonsBeforeCreate = await interventionComponentsPage.countDeleteButtons();

    await interventionComponentsPage.clickOnCreateButton();
    await promise.all([
      interventionUpdatePage.setBeginInput('2000-12-31'),
      interventionUpdatePage.setEndInput('2000-12-31'),
      interventionUpdatePage.trainerSelectLastOption(),
      interventionUpdatePage.formationSessionSelectLastOption()
    ]);
    expect(await interventionUpdatePage.getBeginInput()).to.eq('2000-12-31', 'Expected begin value to be equals to 2000-12-31');
    expect(await interventionUpdatePage.getEndInput()).to.eq('2000-12-31', 'Expected end value to be equals to 2000-12-31');
    await interventionUpdatePage.save();
    expect(await interventionUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await interventionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Intervention', async () => {
    const nbButtonsBeforeDelete = await interventionComponentsPage.countDeleteButtons();
    await interventionComponentsPage.clickOnLastDeleteButton();

    interventionDeleteDialog = new InterventionDeleteDialog();
    expect(await interventionDeleteDialog.getDialogTitle()).to.eq('tothApplicationApp.intervention.delete.question');
    await interventionDeleteDialog.clickOnConfirmButton();

    expect(await interventionComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
