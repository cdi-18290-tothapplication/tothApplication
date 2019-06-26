/* tslint:disable no-unused-expression */
import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TrainerComponentsPage, TrainerDeleteDialog, TrainerUpdatePage } from './trainer.page-object';

const expect = chai.expect;

describe('Trainer e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let trainerUpdatePage: TrainerUpdatePage;
  let trainerComponentsPage: TrainerComponentsPage;
  let trainerDeleteDialog: TrainerDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Trainers', async () => {
    await navBarPage.goToEntity('trainer');
    trainerComponentsPage = new TrainerComponentsPage();
    await browser.wait(ec.visibilityOf(trainerComponentsPage.title), 5000);
    expect(await trainerComponentsPage.getTitle()).to.eq('tothApplicationApp.trainer.home.title');
  });

  it('should load create Trainer page', async () => {
    await trainerComponentsPage.clickOnCreateButton();
    trainerUpdatePage = new TrainerUpdatePage();
    expect(await trainerUpdatePage.getPageTitle()).to.eq('tothApplicationApp.trainer.home.createOrEditLabel');
    await trainerUpdatePage.cancel();
  });

  it('should create and save Trainers', async () => {
    const nbButtonsBeforeCreate = await trainerComponentsPage.countDeleteButtons();

    await trainerComponentsPage.clickOnCreateButton();
    await promise.all([trainerUpdatePage.userSelectLastOption()]);
    await trainerUpdatePage.save();
    expect(await trainerUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await trainerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Trainer', async () => {
    const nbButtonsBeforeDelete = await trainerComponentsPage.countDeleteButtons();
    await trainerComponentsPage.clickOnLastDeleteButton();

    trainerDeleteDialog = new TrainerDeleteDialog();
    expect(await trainerDeleteDialog.getDialogTitle()).to.eq('tothApplicationApp.trainer.delete.question');
    await trainerDeleteDialog.clickOnConfirmButton();

    expect(await trainerComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
