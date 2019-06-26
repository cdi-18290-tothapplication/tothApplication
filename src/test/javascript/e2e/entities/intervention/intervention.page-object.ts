import { browser, ExpectedConditions, element, by, ElementFinder } from 'protractor';

export class InterventionComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-intervention div table .btn-danger'));
  title = element.all(by.css('jhi-intervention div h2#page-heading span')).first();

  async clickOnCreateButton(timeout?: number) {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(timeout?: number) {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons() {
    return this.deleteButtons.count();
  }

  async getTitle() {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class InterventionUpdatePage {
  pageTitle = element(by.id('jhi-intervention-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  beginInput = element(by.id('field_begin'));
  endInput = element(by.id('field_end'));
  trainerSelect = element(by.id('field_trainer'));
  formationSessionSelect = element(by.id('field_formationSession'));

  async getPageTitle() {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setBeginInput(begin) {
    await this.beginInput.sendKeys(begin);
  }

  async getBeginInput() {
    return await this.beginInput.getAttribute('value');
  }

  async setEndInput(end) {
    await this.endInput.sendKeys(end);
  }

  async getEndInput() {
    return await this.endInput.getAttribute('value');
  }

  async trainerSelectLastOption(timeout?: number) {
    await this.trainerSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async trainerSelectOption(option) {
    await this.trainerSelect.sendKeys(option);
  }

  getTrainerSelect(): ElementFinder {
    return this.trainerSelect;
  }

  async getTrainerSelectedOption() {
    return await this.trainerSelect.element(by.css('option:checked')).getText();
  }

  async formationSessionSelectLastOption(timeout?: number) {
    await this.formationSessionSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async formationSessionSelectOption(option) {
    await this.formationSessionSelect.sendKeys(option);
  }

  getFormationSessionSelect(): ElementFinder {
    return this.formationSessionSelect;
  }

  async getFormationSessionSelectedOption() {
    return await this.formationSessionSelect.element(by.css('option:checked')).getText();
  }

  async save(timeout?: number) {
    await this.saveButton.click();
  }

  async cancel(timeout?: number) {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class InterventionDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-intervention-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-intervention'));

  async getDialogTitle() {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(timeout?: number) {
    await this.confirmButton.click();
  }
}
